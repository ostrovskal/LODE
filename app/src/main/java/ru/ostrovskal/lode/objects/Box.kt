package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.utils.dirHorz
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

class Box(x: Int, y: Int): Object(x, y, 1, T_BOX) {

	init { setMap(x, y) }
	
	override fun process(own: ViewGame): Boolean {
		val exact = isExact()
		if(exact) control = if(isProp(x, y + SEGMENTS, FD)) control or MODE_DROP else control and MODE_DROP.inv()
		val xx = x
		val yy = y
		if(control test MODE_DROP) {
			if(count test  1) {
				y++
				setMap(xx, yy)
			}
		}
		else if(Level.isIntersectPerson(x, y + SEGMENTS / 2, false)) {
			val dir = ((Level.person.control and (MODE_RIGHT or MODE_LEFT)) shr 5).dirHorz
			if(exact) {
				if(!isProp(x + dir * SEGMENTS, y, FB)) {
					x += dir
					setMap(xx, yy)
				}
			}
		}
		own.drawTile(x, y, tile)
		return true
	}
	
	private fun setMap(xx: Int, yy: Int) {
		toMap(xx, yy, MSKP.toByte(), OPS_AND)
		toMap(x, y, MSKZ.toByte(), OPS_OR)
	}
}