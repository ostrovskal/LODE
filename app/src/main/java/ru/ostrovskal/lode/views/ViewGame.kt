
package ru.ostrovskal.lode.views

import android.content.Context
import android.graphics.Canvas
import android.os.Message
import android.view.SurfaceHolder
import android.view.View
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Constants.MSG_SERVICE
import com.github.ostrovskal.ssh.Constants.iPt
import com.github.ostrovskal.ssh.STORAGE
import com.github.ostrovskal.ssh.Touch
import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.utils.*
import com.github.ostrovskal.ssh.widgets.Controller
import com.github.ostrovskal.ssh.widgets.Text
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.forms.FormGame
import ru.ostrovskal.lode.msg
import ru.ostrovskal.lode.tables.Level
import java.util.*

class ViewGame(context: Context) : ViewCommon(context) {
	
	// Признак запуска в тестовом режиме
	@STORAGE @JvmField var isTest   = false

	// Признак опытного игрока
	private var isMaster			= KEY_MASTER.optBool
	
	// Главная разметка
	private val main                get() = wnd.main
	
	// курсор
	@JvmField val cursor			= Controller(context, main, R.id.controller, false).apply {
		setControllerMap(lodeController)
		wnd.main.addView(this)
	}
	
	 init {
		main.setOnTouchListener { _, event ->
			Touch.onTouch(event)
			Touch.findTouch(0)?.apply {
				cursor.visibility = View.VISIBLE
				cursor.position = p1.toInt(iPt)
			}
			true
		}
	}
	// Параметры уровня
	@STORAGE @JvmField var params 	= IntArray(PARAMS_COUNT)
	
	// Кэш параметров уровня
	private var paramsCache 		= IntArray(PARAMS_COUNT)
	
	// Обновление счетчиков в основном потоке
	private val updatePanel = Runnable {
		var idx = 0
		wnd.findForm<FormGame>("game")?.root?.loopChildren {
			if(idx test 1) {
				val index = idx / 2
				val value = params[index]
				if(value != paramsCache[index]) {
					paramsCache[index] = value
					(it as? Text)?.text = strBuffer.padZero(value, formatLengths[index], false)
				}
			}
			idx++
		}
	}
	
	override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
		super.surfaceChanged(holder, format, width, height)
		// Добавить контроллер
		main.removeView(cursor)
		main.addView(cursor)
		// счетчики
		Arrays.fill(paramsCache, -1)
	}
	
	override fun handleMessage(msg: Message): Boolean {
		"onMessageViewGame(what: ${msg.what.msg} arg1: ${msg.arg1.msg} arg2: ${msg.arg2} obj: ${msg.obj})".debug()
		if(super.handleMessage(msg)) {
			val s = surHandler
			if(s != null) {
				msg.apply {
					when(what) {
						Constants.MSG_SERVICE -> {
							if(arg1 == ACTION_LOAD) {
								params[PARAM_GOLD]	= Level.gold
								params[PARAM_LEVEL]	= position + 1
								post(updatePanel)
							}
						}
						STATUS_INIT           -> {
							params[PARAM_LIFE]	= if(isTest) 1 else PERSON_LIFE
							params[PARAM_SCORE]	= 0
							params[PARAM_LIMIT]	= if(isMaster) PERSON_ADD_LIMIT * 2 else PERSON_ADD_LIMIT
							status = STATUS_DEAD
							s.send(STATUS_DEAD, a2 = 1)
						}
						STATUS_DEAD           -> {
							status = STATUS_CLEARED
							isDead = false
							s.send(STATUS_CLEARED, a1 = position, a2 = arg2)
						}
						STATUS_CLEARED        -> {
							status = STATUS_PREPARED
							nStart = 50
							s.send(MSG_SERVICE, 0, ACTION_LOAD, arg1)
							// Первый запуск/Следующий уровень - запускаем музыку
							if(arg2 == 1) Sound.playRandomMusic(wnd, true)
						}
						STATUS_PREPARED       -> {
							newStatus = 0
							idMsg = 0
							sysMsg = ""
							status = STATUS_LOOP
							cursor.reset()
						}
						STATUS_SUICIDED       -> {
							if(status == STATUS_LOOP) {
								newStatus = restart()
								status = STATUS_DEAD
								idMsg = if(newStatus == STATUS_EXIT) R.string.msg_game_over else R.string.msg_person_suicyded
								surHandler?.send(STATUS_MESSAGE, 0, newStatus, nArg, idMsg)
							}
						}
					}
				}
			}
		}
		return true
	}
	
	private fun restart(): Int {
		return if(params[PARAM_LIFE] <= 1) {
			STATUS_EXIT
		} else {
			params[PARAM_LIFE]--
			STATUS_DEAD
		}
	}

	override fun draw(canvas: Canvas) {
		super.draw(canvas)
		post(updatePanel)
		if(nStart > 0) nStart--
		if(status == STATUS_LOOP) {
			if(newStatus == 0) {
				if(isDead) {
					idMsg = if(params[PARAM_LIFE] == 1) R.string.msg_game_over else R.string.msg_person_dead
					Sound.playSound(SND_PERSON_DEAD)
				}
				else if(params[PARAM_GOLD] == 0 && Level.person.y < SEGMENTS) {
					Sound.playSound(SND_LEVEL_NEXT)
					idMsg = R.string.msg_next_level
					nArg = ++position
					newStatus = STATUS_CLEARED
					if(!isTest) {
						params[PARAM_LIFE]++
						// разблокировать уровень
						Level.update {
							it[Level.blocked] = 0L
							where { Level.system.eq(Level.pack) and Level.position.eq(Level.num) }
						}
					}
				}
				if(idMsg != 0) {
					if(isTest) {
						idMsg = R.string.msg_test_complete
						newStatus = STATUS_EXIT
					} else {
						if(isDead) newStatus = restart()
					}
					surHandler?.send(STATUS_MESSAGE, MESSAGE_DELAYED, newStatus, nArg, idMsg)
				}
			}
		}
	}
	
	override fun onDetachedFromWindow() {
		main.removeView(cursor)
		super.onDetachedFromWindow()
	}
	
	/**
	 * При активации формы финиша игры - убрать лишиние элементы
	 */
	fun finishForm() {
//		Stat.save(rnd.seed)
		main.removeView(cursor)
		wnd.instanceForm(FORM_FINISH)
	}
	
	fun addScore(o: Int) {
		params[PARAM_SCORE] += valuesAddScores[o]
		if(params[PARAM_SCORE] >= params[PARAM_LIMIT]) {
			params[PARAM_LIFE]++
			params[PARAM_LIMIT] += if(isMaster) PERSON_ADD_LIMIT * 2 else PERSON_ADD_LIMIT
		}
	}

	fun processSpec(x: Int, y: Int, o: Int) {
		addScore(o)
		Sound.playSound(SND_PERSON_TAKE)
		drawTile(x, y, T_NULL, true)
		if(o == O_GOLD) {
			params[PARAM_GOLD]--
			if(params[PARAM_GOLD] == 0) {
				// показать лестницу
				repeat(Level.height) { yy ->
					repeat(Level.width) { xx ->
						if(Level.buffer[xx, yy] and MSKT == T_TRAPN.toInt()) {
							drawTile(xx * SEGMENTS, yy * SEGMENTS, T_TRAP, true)
						}
					}
				}
				Sound.playSound(SND_LEVEL_CLEAR)
			}
		}
	}
}
