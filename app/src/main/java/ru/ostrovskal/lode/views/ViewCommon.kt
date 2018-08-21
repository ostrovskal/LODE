package ru.ostrovskal.lode.views

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Message
import android.view.Gravity
import android.view.SurfaceHolder
import com.github.ostrovskal.ssh.*
import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.utils.*
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.LodeWnd
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.msg
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Pack
import kotlin.math.sign

open class ViewCommon(context: Context) : Surface(context) {
	
	// Признак гибели человека
	@JvmField var isDead                = false
	
	// Размер сегмента тайла на канве
	@JvmField var segTileCanvas         = 0
	
	// Размер спрайта на канве
	@JvmField var canvasTileSize        = 0
	
	private var canvas: Canvas?         = null
	
	@JvmField var position              = 0
	
	// Буфер для форматирования строк
	@JvmField protected val strBuffer	= StringBuilder()
	
	// признак наличия уровня
	val isLevel                         get() = Level.buffer.size > 2
	
	// ссылка на буфер
	private val buffer		            get() = Level.buffer
	
	// вернуть окно
	val wnd                             get() = context as LodeWnd
	
	// превью режим
	@STORAGE @JvmField var preview		= false
	
	// параметры переходного статуса
	@STORAGE @JvmField var newStatus	= 0
	
	@STORAGE @JvmField var nArg 		= 0
	
	@STORAGE @JvmField var idMsg 		= 0
	
	// размер спрайта
	@JvmField var tileBitmapSize 		= 0
	
	// системное сообщение
	@STORAGE @JvmField var sysMsg 		= ""
	
	// статус исполнения
	@STORAGE @JvmField var status 		= STATUS_UNK
	
	// Смещение канвы в сегментах
	@JvmField var mapOffsetSegments		= Point()

	// Габариты канвы
	@JvmField var canvasSegments		= Size()

	// Габариты экрана
	@JvmField var screenSize 			= Size()
	
	// Смещение канвы отрисовки карты
	@JvmField var canvasOffset 			= Point()
	
	// Размер карты
	@JvmField protected var previewMap	= Size()
	
	// Размер блока
	@JvmField protected var previewBlk	= Size()
	
	// Смещение от начала карты
	@JvmField protected var previewMO	= Point()
	
	// Смещение от начала канвы
	@JvmField protected var previewCO	= Point()
	
	// Размер сегмента
	@JvmField protected var previewSeg	= SizeF()
	
	// Тайлы
	private val tiles: Bitmap?          get() = wnd.bitmapGetCache("sprites")
	
	// Ректы
	private var canvasRect 				= Rect()
	
	private var bitmapRect 				= Rect()
	
	private var messageRect 			= RectF()
	
	// Кисть для отрисовки сообщения
	private var sys 					= Paint()
	
	// "Пустая" кисть
	private var nil 					= Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
	
	init {
		delay = LodeWnd.applySpeed(STD_GAME_SPEED)
		tileBitmapSize = (tiles?.height ?: 0) / 8
	}
	
	override fun restoreState(state: Bundle, vararg params: Any?) {
		super.restoreState(state, Level)
	}
	
	override fun saveState(state: Bundle, vararg params: Any?) {
		super.saveState(state, Level)
	}
	
	fun initMap(calcCanvas: Boolean) {
		if(isLevel) {
			if(calcCanvas) {
				Level.mapSegments.set(Level.width * SEGMENTS, Level.height * SEGMENTS)
				val screenSegments = Size(screenSize.w / canvasTileSize * SEGMENTS, screenSize.h / canvasTileSize * SEGMENTS)
				val offsX = screenSize.w - Level.mapSegments.w * segTileCanvas
				val offsY = screenSize.h - Level.mapSegments.h * segTileCanvas

				canvasOffset.set(if(offsX <= 0) 0 else offsX / 2, if(offsY <= 0) screenSize.h % canvasTileSize else offsY / 2)
				canvasSegments.set(if(screenSegments.w > Level.mapSegments.w) Level.mapSegments.w else screenSegments.w,
				                   if(screenSegments.h > Level.mapSegments.h) Level.mapSegments.h else screenSegments.h)
				Touch.reset()
				wnd.wndHandler?.send(MSG_FORM, 0, ACTION_NUM)
				"canvasOffset: $canvasOffset creenSegments: $screenSegments canvasSegments: $canvasSegments screenSize: $screenSize canvasTileSize: $canvasTileSize".debug()
			}
			iPt.set(Level.person.x, Level.person.y)
			mapOffsetSegments.set(iPt.x - canvasSegments.w / 2, iPt.y - canvasSegments.h / 2)
			if(mapOffsetSegments.x < 0) mapOffsetSegments.x = 0
			if(mapOffsetSegments.y < 0) mapOffsetSegments.y = 0
			if(mapOffsetSegments.x + canvasSegments.w >= Level.mapSegments.w) mapOffsetSegments.x = (Level.mapSegments.w - canvasSegments.w)
			if(mapOffsetSegments.y + canvasSegments.h >= Level.mapSegments.h) mapOffsetSegments.y = (Level.mapSegments.h - canvasSegments.h)
			updatePreview(preview, calcCanvas)
		}
	}
	
	fun updatePreview(prev: Boolean, full: Boolean) {
		previewMO.x = if(prev) 0 else mapOffsetSegments.x
		previewMO.y = if(prev) 0 else mapOffsetSegments.y
		if(full) {
			val h = Level.height.toFloat()
			val w = Level.width.toFloat()
			previewMap.h = if(prev) h.toInt() else Math.round(canvasSegments.h / SEGMENTS.toFloat())
			previewMap.w = if(prev) w.toInt() else Math.round(canvasSegments.w / SEGMENTS.toFloat())
			previewBlk.h = if(prev) (screenSize.h / h).toInt() else canvasTileSize
			previewBlk.w = if(prev) (screenSize.w / w).toInt() else canvasTileSize
			previewCO.x = if(prev) ((screenSize.w - (w * previewBlk.w)) / 2f).toInt() else canvasOffset.x
			previewCO.y = if(prev) ((screenSize.h - (h * previewBlk.h)) / 2f).toInt() else canvasOffset.y
			previewSeg.h = (if(prev) previewBlk.h / SEGMENTS else segTileCanvas).toFloat()
			previewSeg.w = (if(prev) previewBlk.w / SEGMENTS else segTileCanvas).toFloat()
		}
		if(this is ViewEditor) KEY_EDIT_PREVIEW.optBool = prev
	}
	
	override fun draw(canvas: Canvas) {
		super.draw(canvas)
		if(isLevel) {
			this@ViewCommon.canvas = canvas
			val xo = previewMO.x / SEGMENTS
			val yo = previewMO.y / SEGMENTS
			val xoo = previewMO.x % SEGMENTS
			val yoo = previewMO.y % SEGMENTS
			val w = previewMap.w + xoo.sign
			val h = previewMap.h + yoo.sign
			
			val coX = previewCO.x - xoo * segTileCanvas
			val coY = previewCO.y - yoo * segTileCanvas
			
			repeat(h) {y ->
				canvasRect.top = coY + y * previewBlk.h
				canvasRect.bottom = canvasRect.top + previewBlk.h
				repeat(w) { x ->
					val tile = buffer[xo + x, yo + y]  and MSKT
					val v = if(this is ViewGame) remapTiles[tile] else remapEditorTiles[tile]
					if(v != T_NULL.toInt()) {
						bitmapRect.left = v % 10 * tileBitmapSize
						bitmapRect.top = v / 10 * tileBitmapSize
						bitmapRect.right = bitmapRect.left + tileBitmapSize
						bitmapRect.bottom = bitmapRect.top + tileBitmapSize
						canvasRect.left = coX + x * previewBlk.w
						canvasRect.right = canvasRect.left + previewBlk.w
						canvas.drawBitmap(tiles, bitmapRect, canvasRect, nil)
					}
				}
			}
			if(this is ViewGame) {
				var idx = 0
				while(idx < Level.pool.size) {
					val o = Level.pool[idx]
					if(!o.process(this)) {
						// объект уничтожен
						Level.pool.removeAt(idx)
						idx--
					}
					idx++
				}
				isDead = !Level.person.process(this)
			}
			// ограничитель экрана сверху(плавное отображение при сдвиге)
			if(canvasOffset.y != 0 && !preview) canvas.drawRect(0f, 0f, screenSize.w.toFloat(), canvasOffset.y.toFloat(), nil)
			sys.drawTextInBounds(canvas, sysMsg, messageRect, Gravity.CENTER)
		} else if(this is ViewEditor) {
			sys.drawTextInBounds(canvas, wnd.getString(R.string.level_not_found), messageRect, Gravity.CENTER)
		}
	}
	
	override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
		super.surfaceChanged(holder, format, width, height)
		// применить масштаб
		canvasTileSize = (LodeWnd.applyScale((if(width > height) height else width) / (12 * config.multiplySW).toInt())) and -4
		segTileCanvas = canvasTileSize / SEGMENTS
		screenSize.set(width, height)
		messageRect.right = width.toFloat(); messageRect.bottom = height.toFloat()
		sys.apply {
			color = Theme.integer(context, StylesAndAttrs.ATTR_SSH_COLOR_MESSAGE or StylesAndAttrs.THEME)
			textSize = Theme.dimen(context, R.dimen.msg).toFloat()
			textAlign = Paint.Align.CENTER
			typeface = context.makeFont("large")
			setShadowLayer(Theme.dimen(context, R.dimen.shadowTextR) * 2f,
			               Theme.dimen(context, R.dimen.shadowTextX) * 2f,
			               Theme.dimen(context, R.dimen.shadowTextY) * 2f,
			               0x0.color)
		}
		if(canvasSegments.isEmpty()) initMap(true)
	}

	override fun handleMessage(msg: Message): Boolean {
		"onMessageCommonView(what: ${msg.what.msg} arg1: ${msg.arg1.msg} arg2: ${msg.arg2} obj: ${msg.obj})".debug()
		val h = wnd.wndHandler
		val s = surHandler
		val editor = (this@ViewCommon as? ViewEditor)
		if(h != null && s != null) {
			if(wnd.restart) {
				wnd.restart = false
				if(newStatus != 0) s.send(STATUS_MESSAGE, 0, newStatus, nArg, idMsg)
				else when(status) {
					STATUS_CLEARED	-> s.send(STATUS_CLEARED, a1 = position)
					STATUS_UNK		-> s.send(STATUS_INIT)
					else			-> s.send(status)
				}
				return false
			} else {
				msg.apply {
					when(what) {
						STATUS_MESSAGE          -> {
							// изменение статуса с задержкой
							sysMsg = resources.getString(obj as? Int ?: 0)
							s.send(arg1, MESSAGE_DELAYED, arg2)
						}
						MSG_SERVICE             -> when(arg1) {
							ACTION_LOAD     -> {
								// загружаем карту
								position = msg.arg2
								val success = Level.load(position, editor == null)
								if(success) {
									initMap(true)
									if(editor != null) editor.modify = false
									else {
										strBuffer.fmt("${resources.getString(R.string.level)}%3d", Level.num.toInt() + 1)
										sysMsg = strBuffer.toString()
									}
									s.send(STATUS_PREPARED, MESSAGE_DELAYED)
								}
								else {
									if(editor == null) {
										// НЕ УДАЛОСЬ ЗАГРУЗИТЬ ПЛАНЕТУ - ВОЗМОЖНО ОНА БЫЛА ПОСЛЕДНЕЙ
										sysMsg = ""
										h.send(MSG_FORM, a1 = ACTION_FINISH)
									}
								}
							}
							ACTION_SAVE     -> {
								val success = Level.store(context)
								if(arg2 == 1) h.send(Constants.MSG_FORM, a1 = Constants.ACTION_EXIT)
								else {
									editor?.modify = false
									s.send(STATUS_MESSAGE, a1 = STATUS_PREPARED, o = if(success) R.string.save_level_success else R.string.save_level_failed)
								}
							}
							ACTION_DELETE   -> {
								val count = Pack.countLevels(Level.pack) - 1
								Level.delete(true)
								h.send(Constants.MSG_FORM, a1 = ACTION_LOAD, a2 = if(position >= count) position - 1 else position)
								editor?.modify = false
							}
							ACTION_NEW      -> {
								//Level.generator(context, arg2)
								editor?.modify = false
								initMap(true)
							}
							ACTION_GENERATE -> {
								Level.default(context)
								h.send(Constants.MSG_FORM, a1 = ACTION_LOAD, a2 = 0)
								editor?.modify = false
							}
						}
						STATUS_EXIT             -> h.send(MSG_FORM, a1 = ACTION_EXIT)
					}
				}
			}
		}
		return super.handleMessage(msg)
	}
	
	fun drawTile(x: Int, y: Int, t: Byte): Boolean {
		val xx = x - previewMO.x
		val yy = y - previewMO.y
		val res = (xx > -SEGMENTS && yy > -SEGMENTS && xx < canvasSegments.w && yy < canvasSegments.h)
		if(res) {
			bitmapRect.left = t % 10 * tileBitmapSize
			bitmapRect.top = t / 10 * tileBitmapSize
			bitmapRect.right = bitmapRect.left + tileBitmapSize
			bitmapRect.bottom = bitmapRect.top + tileBitmapSize
			canvasRect.top = previewCO.y + yy * segTileCanvas
			canvasRect.bottom = canvasRect.top + previewBlk.h
			canvasRect.left = previewCO.x + xx * segTileCanvas
			canvasRect.right = canvasRect.left + previewBlk.w
			canvas?.drawBitmap(tiles, bitmapRect, canvasRect, nil)
		}
		return res
	}
}
