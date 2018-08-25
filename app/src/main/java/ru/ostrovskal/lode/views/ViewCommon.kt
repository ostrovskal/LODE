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

open class ViewCommon(context: Context) : Surface(context) {
	
	// Признак гибели человека
	@JvmField var isDead                = false
	
	// Размер сегмента тайла на канве
	@JvmField var segTileCanvas         = 0
	
	// Размер спрайта на канве
	@JvmField var canvasTileSize        = 0
	
	private var canvas: Canvas?         = null

	// Счетчик при старте
	@STORAGE @JvmField var nStart       = 100
	
	// Позиция уровня
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
	@JvmField var mapOffsetSegments		= Point(-1000, -1000)

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
	
	private var bg: Bitmap?             = null
	
	// "Пустая" кисть
	private var nil 					= Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
	
	init {
		delay = LodeWnd.applySpeed(STD_GAME_SPEED)
		tileBitmapSize = (tiles?.height ?: 0) / 10
	}
	
	override fun restoreState(state: Bundle, vararg params: Any?) {
		super.restoreState(state, Level)
	}
	
	override fun saveState(state: Bundle, vararg params: Any?) {
		super.saveState(state, Level)
	}
	
	fun updatePreview(prev: Boolean, full: Boolean) {
		previewMO.x = if(prev) 0 else mapOffsetSegments.x
		previewMO.y = if(prev) 0 else mapOffsetSegments.y
		if(full) {
			val h = Level.height.toFloat()
			val w = Level.width.toFloat()
			previewMap.h = if(prev) h.toInt() else canvasSegments.h / SEGMENTS
			previewMap.w = if(prev) w.toInt() else canvasSegments.w / SEGMENTS
			previewBlk.h = if(prev) (screenSize.h / h).toInt() else canvasTileSize
			previewBlk.w = if(prev) (screenSize.w / w).toInt() else canvasTileSize
			previewCO.x = if(prev) ((screenSize.w - (w * previewBlk.w)) / 2f).toInt() else canvasOffset.x
			previewCO.y = if(prev) ((screenSize.h - (h * previewBlk.h)) / 2f).toInt() else canvasOffset.y
			previewSeg.h = (if(prev) previewBlk.h / SEGMENTS else segTileCanvas).toFloat()
			previewSeg.w = (if(prev) previewBlk.w / SEGMENTS else segTileCanvas).toFloat()
		}
		if(this is ViewEditor) KEY_EDIT_PREVIEW.optBool = prev
	}
	
	fun prepareMap(full: Boolean, offset: Boolean) {
		if(isLevel) {
			sleepThread = true
			
			if(full) {
				Level.mapSegments.set(Level.width * SEGMENTS, Level.height * SEGMENTS)
				val screenSegments = Size(screenSize.w / canvasTileSize * SEGMENTS, screenSize.h / canvasTileSize * SEGMENTS)
				val offsX = screenSize.w - Level.mapSegments.w * segTileCanvas
				val offsY = screenSize.h - Level.mapSegments.h * segTileCanvas
				
				canvasOffset.set(if(offsX <= 0) 0 else offsX / 2, if(offsY <= 0) screenSize.h % canvasTileSize else offsY / 2)
				canvasSegments.set(if(screenSegments.w > Level.mapSegments.w) Level.mapSegments.w else screenSegments.w,
				                   if(screenSegments.h > Level.mapSegments.h) Level.mapSegments.h else screenSegments.h)
				Touch.reset()
				val w = (canvasSegments.w + SEGMENTS) * segTileCanvas
				val h = (canvasSegments.h + SEGMENTS) * segTileCanvas
				bg = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
				"bitmap bg [$w - $h]".debug()
				Level.useMap = true
			}
			var oldX = -1000
			var oldY = -1000
			if(offset) {
				oldX = mapOffsetSegments.x / SEGMENTS
				oldY = mapOffsetSegments.y / SEGMENTS
				iPt.set(Level.person.x, Level.person.y)
				mapOffsetSegments.set(iPt.x - canvasSegments.w / 2, iPt.y - canvasSegments.h / 2)
			}
			if(mapOffsetSegments.x < 0) mapOffsetSegments.x = 0
			if(mapOffsetSegments.y < 0) mapOffsetSegments.y = 0
			val isLimitWidth = mapOffsetSegments.x + canvasSegments.w >= Level.mapSegments.w
			val isLimitHeight = mapOffsetSegments.y + canvasSegments.h >= Level.mapSegments.h
			if(isLimitWidth) mapOffsetSegments.x = Level.mapSegments.w - canvasSegments.w
			if(isLimitHeight) mapOffsetSegments.y = Level.mapSegments.h - canvasSegments.h
			updatePreview(preview, full)

			if(mapOffsetSegments.x / SEGMENTS != oldX || mapOffsetSegments.y / SEGMENTS != oldY || full) {

				val w = previewMap.w + if(isLimitWidth || preview) 0 else 1
				val h = previewMap.h + if(isLimitHeight || preview) 0 else 1
				val xo = previewMO.x / SEGMENTS
				val yo = previewMO.y / SEGMENTS
				
				val c = Canvas(bg)
				
				c.drawColor(0.color)
				
				val msk: Int
				
				val remap = if(this is ViewGame) {
					msk = MSKT
					remapGameTiles
				} else {
					msk = MSKO
					remapEditorTiles
				}
				
				repeat(h) { y ->
					canvasRect.top = y * previewBlk.h
					canvasRect.bottom = canvasRect.top + previewBlk.h
					repeat(w) { x ->
						val tile = buffer[xo + x, yo + y] and msk
						val v = remap[tile]
						bitmapRect.left = v % TILES_HORZ * tileBitmapSize
						bitmapRect.top = v / TILES_HORZ * tileBitmapSize
						bitmapRect.right = bitmapRect.left + tileBitmapSize
						bitmapRect.bottom = bitmapRect.top + tileBitmapSize
						canvasRect.left = x * previewBlk.w
						canvasRect.right = canvasRect.left + previewBlk.w
						c.drawBitmap(tiles, bitmapRect, canvasRect, nil)
					}
				}
			}
			sleepThread = false
		}
	}
	
	override fun draw(canvas: Canvas) {
		super.draw(canvas)
		if(isLevel) {
			if(!Level.useMap) prepareMap(true, true)
			
			this@ViewCommon.canvas = canvas
			
			val offsX = (previewMO.x % SEGMENTS) * segTileCanvas
			val offsY = (previewMO.y % SEGMENTS) * segTileCanvas
			val w = previewMap.w * previewBlk.w
			val h = previewMap.h * previewBlk.h
			bitmapRect.set(offsX, offsY, offsX + w, offsY + h)
			canvasRect.set(previewCO.x, previewCO.y, previewCO.x + w, previewCO.y + h)
			canvas.drawBitmap(bg, bitmapRect, canvasRect, nil)
			
			if(this is ViewGame) {
				var idx = 0
				while(idx < Level.pool.size) {
					val o = Level.pool[idx]
					o.count++
					if(!o.process(this)) {
						// объект уничтожен
						Level.pool.removeAt(idx)
						idx--
					}
					idx++
				}
				isDead = !Level.person.process(this)
			}

			sys.drawTextInBounds(canvas, sysMsg, messageRect, Gravity.CENTER)
		}
		else if(this is ViewEditor) {
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
								h.send(MSG_FORM, a1 = ACTION_NAME)
								if(success) {
									if(editor != null) editor.modify = false else sysMsg = Level.name
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
									h.send(MSG_FORM, a1 = ACTION_NAME)
								}
							}
							ACTION_PROP     -> {
								editor?.modify = true
								h.send(MSG_FORM, a1 = ACTION_NAME)
							}
							ACTION_DELETE   -> {
								val count = Pack.countLevels(Level.pack) - 1
								Level.delete(true)
								h.send(Constants.MSG_FORM, a1 = ACTION_LOAD, a2 = if(position >= count) position - 1 else position)
								editor?.modify = false
							}
							ACTION_NEW      -> {
								Level.generator(context, arg2)
								editor?.modify = false
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
	
	fun drawTile(x: Int, y: Int, t: Byte, isBg: Boolean = false): Boolean {
		val xx = x - previewMO.x
		val yy = y - previewMO.y
		val res = (xx > -SEGMENTS && yy > -SEGMENTS && xx < canvasSegments.w && yy < canvasSegments.h)
		if(res) {
			bitmapRect.left = t % TILES_HORZ * tileBitmapSize
			bitmapRect.top = t / TILES_HORZ * tileBitmapSize
			bitmapRect.right = bitmapRect.left + tileBitmapSize
			bitmapRect.bottom = bitmapRect.top + tileBitmapSize
			canvasRect.left = xx * segTileCanvas
			canvasRect.right = canvasRect.left + previewBlk.w
			canvasRect.top = yy * segTileCanvas
			canvasRect.bottom = canvasRect.top + previewBlk.h
			if(isBg) {
				Level.toMap(x, y, t)
				Canvas(bg).drawBitmap(tiles, bitmapRect, canvasRect, nil)
			}
			canvasRect.left += previewCO.x
			canvasRect.right += previewCO.x
			canvasRect.top += previewCO.y
			canvasRect.bottom += previewCO.y
			canvas?.drawBitmap(tiles, bitmapRect, canvasRect, nil)
		}
		return res
	}
	
	override fun onDetachedFromWindow() {
		bg?.recycle()
		super.onDetachedFromWindow()
	}
}
