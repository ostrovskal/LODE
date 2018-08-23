package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

class Bridge(x: Int, y: Int, len: Int): Object(x, y, len, T_BRIDGE0) {
	
	override fun process(own: ViewGame): Boolean {
		count--
		if(Level.useButton) {
			if(count == 24) toMap(x, y, O_BRIDGE.toByte(), OPS_SET, len)
			if(count < 48) count++
		} else if(count > 20) {
			count--
			if(count == 22) toMap(x, y, T_NULL, OPS_SET, len)
		}
		if(count > 20) {
			val v = if(count > 28) count - 8 else 20
			repeat(len) { own.drawTile(x + it * SEGMENTS, y, showBridgeTiles[(count - v) shr 1]) }
		}
		return true
	}
}