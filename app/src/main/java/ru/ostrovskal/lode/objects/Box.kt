package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level.fromMap
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

class Box(x: Int, y: Int): Object(x, y, 1, T_BOX) {

	init { setMap(x, y) }
	
	override fun process(own: ViewGame): Boolean {
		if(own.nStart == 0) {
			if(isExact()) {
				if(remapProp[fromMap(x, y)] and MSKO == O_WALL) return false
				control = if(isProp(x, y + SEGMENTS, FB)) control and MODE_DROP.inv() else control or MODE_DROP
			}
			if(control test MODE_DROP) {
				if(count test 1) {
					y++
					setMap(x, y - 1)
				}
			}
		}
		own.drawTile(x, y, tile)
		return true
	}
	
	fun setMap(xx: Int, yy: Int) {
		toMap(xx, yy, MSKT.toByte(), 1, OPS_AND)
		toMap(x, y, MSKX.toByte(), 1, OPS_OR)
	}
}