package ru.ostrovskal.lode.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.os.Message
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import com.github.ostrovskal.ssh.AnimFrames
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Constants.fPt
import com.github.ostrovskal.ssh.STORAGE
import com.github.ostrovskal.ssh.Touch
import com.github.ostrovskal.ssh.utils.*
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.forms.FormEditor
import ru.ostrovskal.lode.msg
import ru.ostrovskal.lode.tables.Level

class ViewEditor(context: Context) : ViewCommon(context), AnimFrames.Callback {
	
	// Анимация, в качестве следящей за режимом сдвига
	private val anim                    = AnimFrames(this, Int.MAX_VALUE, 100, this)
	
	// Временная позиция карты. Используется при перетаскивании
	private var moffs                   = Point()
	
	// признак перерисовки карты
	private var updateMap               = false
	
	// режим сдвига
	@STORAGE @JvmField var modeShift    = SHIFT_UNDEF
	
	// содержимое под дроидом
	@STORAGE @JvmField var contentPerson= O_NULL.toByte()
	
	// признак модификации содержимого
	@STORAGE @JvmField var modify       = false
	
	// чуствительность перетаскивания
	private var dragSensitive           = Size()
	
	init {
		anim.start(false, false)
	}
	
	override fun doFrame(view: View, frame: Int, direction: Int, began: Boolean): Boolean {
		if(status == STATUS_LOOP && wnd.formTag == "editor") {
			// обработка сдвига
			if(modeShift == SHIFT_UNDEF && !preview) {
				Touch.findTouch(0)?.apply {
					val time = System.currentTimeMillis()
					if((time - t0) > 300) {
						modeShift = SHIFT_MAP
						moffs.set(mapOffsetSegments.x, mapOffsetSegments.y)
						view.invalidate()
					}
				}
			}
		}
		return false
	}
	
	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(ev: MotionEvent): Boolean {
		Touch.onTouch(ev)
		if(status == STATUS_LOOP) {
			// обработка сдвига
			Touch.drag(0, dragSensitive) { offs, time, t, event ->
				// при первом уведомлении устанавливаем режим
				if(modeShift == SHIFT_UNDEF) {
					modeShift = if(!preview && time > 200) SHIFT_MAP else SHIFT_TILE
				}
				if(event) {
					if(modeShift == SHIFT_MAP) {
						mapOffsetSegments.x = moffs.x - offs.w / segTileCanvas
						mapOffsetSegments.y = moffs.y - offs.h / segTileCanvas
						updateMap = true
					}
				}
				if(modeShift == SHIFT_TILE) {
					t.resetPosition()
					fPt.set(t.p0.x + offs.w, t.p0.y + offs.h)
					shiftTile(t.p1)
				}
				if(!event) modeShift = SHIFT_UNDEF
			}
			return true
		}
		return super.onTouchEvent(ev)
	}
	
	// установка элемента
	private fun shiftTile(abs: PointF) {
		Level.apply {
			val xx = previewMO.x / SEGMENTS + (Math.round(abs.x - previewCO.x + ((previewMO.x % SEGMENTS) * segTileCanvas)) / previewBlk.w)
			val yy = previewMO.y / SEGMENTS + (Math.round(abs.y - previewCO.y + ((previewMO.y % SEGMENTS) * segTileCanvas)) / previewBlk.h)
			// если по границе карты - пропускаем
			if(xx < 0 || yy < 0 || xx >= width || yy >= height) return@apply
			// текущий тайл
			var c = wnd.findForm<FormEditor>("editor")?.curTile?.num ?: return@apply
			c = remapEditorTiles.search(c, T_NULL.toInt())
			// содержимое карты по координатам
			val t = buffer[xx, yy]
			// объект на карте
			val objMap = remapEditorProp[t] and MSKO
			// текущий объект
			val objTile = remapEditorProp[c] and MSKO
			if(objMap != objTile) {
				with(Level) {
					// если ставим поверх персонажа - стираем его координаты
					if(remapEditorProp[t] and MSKO == O_PERSON) person.x = -1
					// если устанавливаем персонажа - старого стираем
					else if(remapEditorProp[c] and MSKO == O_PERSON) {
						if(person.x >= 0) toMap(person.x, person.y, contentPerson)
						contentPerson = t.toByte()
						person.init(xx, yy)
					}
				}
				buffer[xx, yy] = c
				modify = true
			}
			updateMap = true
		}
	}
	
	override fun restoreState(state: Bundle, vararg params: Any?) {
		super.restoreState(state)
		wnd.findForm<FormEditor>("editor")?.setTile(KEY_EDIT_TILE.optInt)
		updatePreview(preview, true)
	}
	
	override fun handleMessage(msg: Message): Boolean {
		"onMessageViewEditor(what: ${msg.what.msg} arg1: ${msg.arg1.msg} arg2: ${msg.arg2} obj: ${msg.obj})".debug()
		if(super.handleMessage(msg)) {
			when(msg.what) {
				STATUS_INIT     -> surHandler?.send(Constants.MSG_SERVICE, 0, ACTION_LOAD, position)
				STATUS_PREPARED -> { sysMsg = ""; status = STATUS_LOOP }
			}
		}
		return true
	}
	
	override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
		super.surfaceChanged(holder, format, width, height)
		dragSensitive.w = canvasTileSize / 2
		dragSensitive.h = canvasTileSize / 2
	}
	
	override fun draw(canvas: Canvas) {
		if(updateMap) {
			prepareMap(false, false)
			updateMap = false
		}
		super.draw(canvas)
		if(modeShift == SHIFT_MAP) {
			// нарисовать признак перетаскивания карты
			canvas.drawColor(0x60404040)
		}
	}
}
