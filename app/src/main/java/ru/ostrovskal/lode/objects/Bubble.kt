package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.utils.ntest
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.views.ViewGame

class Bubble(x: Int, y: Int) : Object(x, y, 1, T_BUBBLE0) {
	
	private var dx          = 0
	
	init {
		control = (Level.rnd.nextInt(3) + 1) * SEGMENTS
		dx = control / 4
	}
	override fun process(own: ViewGame): Boolean {
		var t = ((count / 4) / dx) * 2 + (count and 1)
		if(isProp(x, y, FB)) t = 7
		if(count ntest 3 && t < 6) y--
		own.drawTile(x, y, (tile + t).toByte())
		return t < 7
	}
}
