package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.utils.flags
import com.github.ostrovskal.ssh.utils.get
import com.github.ostrovskal.ssh.utils.set
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

open class Platform(x: Int, y: Int, length: Int, @JvmField protected val vert: Boolean) : Object(x, y, length, 0) {
	private var dir     = Constants.DIRN

	init {
		val r = x flags 1
		dir = if(vert) {
			if(r) Constants.DIRU else Constants.DIRD
		} else {
			if(r) Constants.DIRL else Constants.DIRR
		}
	}
	override fun process(own: ViewGame): Boolean {
		count++
		if(count flags 1) {
			// стереть из массива
			setMap(true)
			val exact = isExact()
			if(Level.useButton || !exact) {
				var xx = x / SEGMENTS
				var yy = y / SEGMENTS
				if(vert) {
					if(exact) {
						yy += if(dir == Constants.DIRU) -1 else 1
						var isMove = (yy >= 0 && yy < Level.height)
						if(isMove) {
							for(index in 0 until len) {
								val vv = Level.buffer[xx + index, yy]
								if(isPropExact(xx + index, yy, FB)) {
									isMove = false
									break
								}
							}
						}
						if(!isMove) dir = if(dir == Constants.DIRU) Constants.DIRD else Constants.DIRU
						
					}
					y += if(dir == Constants.DIRU) -1 else 1
				}
				else {
					if(exact) {
						xx += if(dir == Constants.DIRR) len else -1
						if(xx < 0 || xx >= Level.width || isPropExact(xx, yy, FB)) dir = if(dir == Constants.DIRR) Constants.DIRL else Constants.DIRR
					}
					x += if(dir == Constants.DIRL) -1 else 1
				}
			}
			// записать в массив
			setMap(false)
		}
		if(this is Polz) {
			own.drawTile(x, y, T_POLZ_H_BEGIN)
			repeat(len - 2) { own.drawTile(x + SEGMENTS + it * SEGMENTS, y, T_POLZ_H_MIDDLE) }
			own.drawTile(x + (len - 1) * SEGMENTS, y, T_POLZ_H_END)
		} else {
			repeat(len) { own.drawTile(x + it * SEGMENTS, y, T_WALL) }
		}
		return true
	}
	
	private fun setMap(isClear: Boolean) {
		val xx = x / SEGMENTS
		val yy = y / SEGMENTS
		val w = len + if(x % SEGMENTS > 1) 1 else 0
		val dy = if(y % SEGMENTS > 1) 0 else 0
		repeat(w) {
			var t = Level.buffer[xx + it, yy + dy]
			t = if(isClear) t and MSKP else t or MSKZ
			Level.buffer[xx + it, yy + dy] = t
		}
	}
}