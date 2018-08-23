package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.utils.dirHorz
import com.github.ostrovskal.ssh.utils.dirVert
import com.github.ostrovskal.ssh.utils.info
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.prop
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

open class Platform(x: Int, y: Int, length: Int, @JvmField protected val vert: Boolean, t: Byte = T_WALL) : Object(x, y, length, t) {

	init {
		control = ((x and 1) + 1) shl if(vert) 1 else 3
		toMap(x, y, MSKZ.toByte(), len, OPS_OR)
	}
	
	fun isProp1(x: Int, y: Int, p: Int, l: Int = 1, ops: Int = OPS_FULL): Boolean {
		repeat(l) {
			val v = Level.fromMap(x + it * SEGMENTS, y)
			//v.arr.info()
			val ps = remapProp[v]
			ps.prop.info()
			if(ps test p) return true
		}
		return false
	}

	override fun process(own: ViewGame): Boolean {
		if(count test 1) {
			// стереть из массива
			toMap(x, y, MSKP.toByte(), len + if(x % SEGMENTS > 1) 1 else 0, OPS_AND)
			val exact = isExact()
			if(Level.useButton || !exact) {
				if(vert) {
					if(exact) {
						val yy = y + control.dirVert * SEGMENTS
						if(yy < 0 || yy >= Level.mapSegments.h || isProp(x, yy, FB, len))
							control = control xor DIRV
					}
					y += control.dirVert
				}
				else {
					if(exact) {
						val xx = x + (if(control == DIRR) len else -1) * SEGMENTS
						if(xx < 0 || xx >= Level.mapSegments.w || isProp(xx, y, FB))
							control = control xor DIRH
					}
					x += control.dirHorz
				}
			}
			// записать в массив
			toMap(x, y, MSKZ.toByte(), len + if(x % SEGMENTS > 1) 1 else 0, OPS_OR)
		}
		var dx = 0
		if(this is Polz) {
			dx = 1
			own.drawTile(x, y, T_POLZ_H_BEGIN)
			own.drawTile(x + (len - 1) * SEGMENTS, y, T_POLZ_H_END)
		}
		repeat(len - dx * 2) { own.drawTile(x + (it + dx) * SEGMENTS, y, tile) }
		return true
	}
}