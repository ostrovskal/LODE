package ru.ostrovskal.lode.forms

import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.config
import com.github.ostrovskal.ssh.utils.debug
import com.github.ostrovskal.ssh.utils.dp
import com.github.ostrovskal.ssh.utils.send
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.gameView
import ru.ostrovskal.lode.msg
import ru.ostrovskal.lode.views.ViewGame

class FormGame : Form() {
	// поверхность
	lateinit var game: ViewGame
	
	override fun backPressed() {
		val tm = if(twicePressed()) tmBACK + 2000 else System.currentTimeMillis() + 10000
		if(game.status == STATUS_LOOP) {
			if(tm > System.currentTimeMillis()) {
				// Если тест - просто выход
				if(game.isTest) {
					sendResult(MSG_FORM, ACTION_EXIT)
				} else {
					wnd.instanceForm(FORM_DLG_G_ACTIONS)
				}
			} else {
				wnd.showToast(getString(R.string.again_press_for_exit), true, null, ToastLayout())
			}
		}
		tmBACK = System.currentTimeMillis()
	}
	
	override fun twicePressed() = true
	
	override fun handleMessage(msg: Message) {
		val s = game.surHandler
		if(s != null) {
			msg.apply {
				"onMessageFormGame(what: ${what.msg} arg1: ${arg1.msg} arg2: $arg2 obj: $obj)".debug()
				when(arg1) {
					ACTION_LOAD         -> s.send(a1 = ACTION_LOAD, a2 = arg2)
					ACTION_EXIT         -> footer(Constants.BTN_NO, 0)
					ACTION_FINISH       -> game.finishForm()
				}
			}
		}
	}
	
	override fun inflateContent(container: LayoutInflater): UiCtx {
		val port = config.isVert
		return UI {
			linearLayout {
				containerLayout(100, if(port) 94 else 92, true) {
					id = R.id.gameContainer
					game = gameView { id = R.id.game }
				}.lps(WRAP, WRAP)
				root = cellLayout(if(port) 15 else 28, 3, 1.dp, false) {
					backgroundSet(style_panel)
					val fpos = if(port) 0 else 7
					// очки
					button(style_panel_tile) { numResource = R.integer.TILE_SCORE }.lps(1 + fpos, 1, 1, 2)
					text(R.string.empty_score, style_text_counters).lps(2 + fpos, 1, 6, 2)
					// жизни, золото, уровень
					repeat(3) {
						button(style_panel_tile) { numResource = tilesGamePanel[it] }.lps(6 + fpos + it * 3, 1, 1, 2)
						text(R.string.empty_counter, style_text_counters).lps(7 + fpos + it * 3, 1, 3, 2)
					}
				}
			}
		}
	}
	
	override fun initContent(content: ViewGroup) {
		game.position = arguments.getInt("position")
		game.isTest = arguments.containsKey("isTest")
	}
	
	override fun saveState(state: Bundle) {
		game.saveState(state)
		super.saveState(state)
	}
	
	override fun restoreState(state: Bundle) {
		super.restoreState(state)
		game.restoreState(state)
	}
	
	override fun setAnimation(trans: FragmentTransaction) { trans.setTransition(FragmentTransaction.TRANSIT_NONE) }
	
	override fun onDestroy() {
		game.holder.removeCallback(game)
		super.onDestroy()
	}
}
