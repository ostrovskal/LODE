package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

class Bridge(x: Int, y: Int, len: Int): Object(x, y, len, T_BRIDGE0) {
	
	override fun process(own: ViewGame): Boolean {
		count--
		if(Level.useButton) {
			if(count == 24) toMap(x, y, MSKZ.toByte(), len, OPS_OR)
			count++
		} else {
			if(count > 20) {
				if(count == 22) toMap(x, y, MSKP.toByte(), len, OPS_AND)
				count--
			} else count = 0
		}
		if(count > 20) {
			val v = if(count > 28) 8 else count - 20
			repeat(len) { own.drawTile(x + it * SEGMENTS, y, bridgeTiles[v shr 1]) }
		}
		return true
	}
}