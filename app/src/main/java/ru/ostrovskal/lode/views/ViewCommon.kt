package ru.ostrovskal.lode.views

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.Gravity
import android.view.SurfaceHolder
import com.github.ostrovskal.ssh.*
import com.github.ostrovskal.ssh.utils.*
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.LodeWnd
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.MAP.tileCanvasSize

open class ViewCommon(context: Context) : Surface(context) {

	// Размер карты
	@JvmField protected var previewMap	= Size()
	
	// Размер блока
	@JvmField protected var previewBlk	= Size()
	
	// Смещение от начала карты
	@JvmField protected var previewMO	= Point()
	
	// Смещение от начала канвы
	@JvmField protected var previewCO	= Point()
	
	// признак наличия уровня
	val isLevel                         get() = Level.MAP.buffer.size > 2
	
	// ссылка на буфер
	val buffer				            get() = Level.MAP.buffer
	
	// вернуть окно
	val wnd                             get() = context as LodeWnd
	
	// превью режим
	@STORAGE @JvmField var preview		= false
	
	// признак воспроизведения записи
	@STORAGE @JvmField var record		= 0L
	
	// параметры переходного статуса
	@STORAGE @JvmField var newStatus	= 0
	
	@STORAGE @JvmField var nArg 		= 0
	
	@STORAGE @JvmField var idMsg 		= 0
	
	// размер спрайта
	@JvmField var tileBitmapSize 		= 0
	
	// позиция планеты
	@STORAGE @JvmField var position 	= 0
	
	// системное сообщение
	@STORAGE @JvmField var sysMsg 		= ""
	
	// статус исполнения
	@STORAGE @JvmField var status 		= STATUS_UNK
	
	// смещение началы карты в блоках
	@JvmField var mapOffset 			= Point()
	
	// смещение блока на экране
	@JvmField var blkOffset             = Point()
	
	// смещение экрана отрисовки карты
	@JvmField var canvasOffset 			= Point()
	
	// экран в фактических блоках
	@JvmField var canvasSize 			= Size()
	
	// количество столбцов в тайловой карте
	private var tilesCol 				= 10
	
	// экран в блоках максимум
	private var canvasMaxSize 			= Size()
	
	// тайлы
	private val tiles: Bitmap?          get() = wnd.bitmapGetCache("sprites")
	
	// ректы
	private var canvasRect 				= Rect()
	
	private var bitmapRect 				= Rect()
	
	private var messageRect 			= RectF()
	
	// кисть для отрисовки сообщения
	private var sys 					= Paint()
	
	// "пустая" кисть
	private var nil 					= Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
	
	init {
		delay = LodeWnd.applySpeed(STD_GAME_SPEED)
		tileBitmapSize = (tiles?.height ?: 0) / 6
	}
	
	override fun restoreState(state: Bundle, vararg params: Any?) {
		super.restoreState(state, Level.MAP)
	}
	
	override fun saveState(state: Bundle, vararg params: Any?) {
		super.saveState(state, Level.MAP)
	}
	
	protected fun initMap(calcCanvas: Boolean) {
		Level.MAP.apply {
			if(isLevel) {
				if(calcCanvas) {
					val mapOffs 	= Point(this@ViewCommon.width - width * tileCanvasSize,
					                       this@ViewCommon.height - height * tileCanvasSize)
					val canvasOffs 	= Point((this@ViewCommon.width - canvasMaxSize.w * tileCanvasSize) / 2,
					                          (this@ViewCommon.height - canvasMaxSize.h * tileCanvasSize) / 2)
					canvasOffset.set(if(mapOffs.x < 0) canvasOffs.x else mapOffs.x / 2, if(mapOffs.y < 0) canvasOffs.y else mapOffs.y / 2)
					canvasSize.set(if(canvasMaxSize.w > width) width else canvasMaxSize.w, if(canvasMaxSize.h > height) height else canvasMaxSize.h)
					Touch.reset()
				}
				val pt = personPos()
				val personOffs = Point(pt.x - canvasMaxSize.w / 2, pt.y - canvasMaxSize.h / 2)
				mapOffset.set(if(personOffs.x < 0) 0 else personOffs.x, if(personOffs.y < 0) 0 else personOffs.y)
				if(mapOffset.x + canvasSize.w > width) mapOffset.x = width - canvasSize.w
				if(mapOffset.y + canvasSize.h > height) mapOffset.y = height - canvasSize.h
				updatePreview(preview, calcCanvas)
			}
		}
	}
	
	override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
		super.surfaceChanged(holder, format, width, height)
		// применить масштаб
		tileCanvasSize = LodeWnd.applyScale((if(width > height) height else width) / (12 * config.multiplySW).toInt())
		canvasMaxSize = Size(width / tileCanvasSize, height / tileCanvasSize)
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
		if(canvasSize.isEmpty()) initMap(true)
	}
	
/*
	override fun handleMessage(msg: Message): Boolean
	{
		return super.handleMessage(msg)
	}
*/
	
	override fun draw(canvas: Canvas) {
		super.draw(canvas)
		if(isLevel) {
			for(y in 0 until previewMap.h) {
				canvasRect.top = previewCO.y + y * previewBlk.h
				canvasRect.bottom = canvasRect.top + previewBlk.h
				for(x in 0 until previewMap.w) {
					val tile = buffer[previewMO.x + x, previewMO.y + y] and MSKT
					val v = if(this !is ViewGame) remapTiles[tile] else tile
					if(v != T_NULL.toInt()) {
						bitmapRect.left = v % tilesCol * tileBitmapSize
						bitmapRect.top = v / tilesCol * tileBitmapSize
						bitmapRect.right = bitmapRect.left + tileBitmapSize
						bitmapRect.bottom = bitmapRect.top + tileBitmapSize
						canvasRect.left = previewCO.x + x * previewBlk.w
						canvasRect.right = canvasRect.left + previewBlk.w
						canvas.drawBitmap(tiles, bitmapRect, canvasRect, nil)
					}
				}
			}
			sys.drawTextInBounds(canvas, sysMsg, messageRect, Gravity.CENTER)
		} else if(this is ViewEditor) {
			sys.drawTextInBounds(canvas, wnd.getString(R.string.app_name), messageRect, Gravity.CENTER)
		}
	}
	
	fun updatePreview(v: Boolean, full: Boolean) {
		previewMO.x = if(v) 0 else mapOffset.x
		previewMO.y = if(v) 0 else mapOffset.y
		if(full) {
			val h = Level.MAP.height.toFloat()
			val w = Level.MAP.width.toFloat()
			previewMap.h = if(v) h.toInt() else canvasSize.h
			previewMap.w = if(v) w.toInt() else canvasSize.w
			previewBlk.h = if(v) (height / h).toInt() else tileCanvasSize
			previewBlk.w = if(v) (width / w).toInt() else tileCanvasSize
			previewCO.x = if(v) ((width - (w * previewBlk.w)) / 2f).toInt() else canvasOffset.x
			previewCO.y = if(v) ((height - (h * previewBlk.h)) / 2f).toInt() else canvasOffset.y
		}
		if(this is ViewEditor) KEY_EDIT_PREVIEW.optBool = v
	}

}