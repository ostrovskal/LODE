package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.singleton.Sound
import ru.ostrovskal.lode.Constants
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

class Wall(x: Int, y: Int) : Object(x, y, 1, T_DESTR0) {
	
	init {
		Sound.playSound(Constants.SND_PERSON_DESTR)
		toMap(x, y, O_NULL.toByte())
	}
	
	override fun process(own: ViewGame): Boolean {
		if(count < 10 || count >= 100) {
			val t = destrTiles[(count - (if(count >= 100) 90 else 1)) shr 1]
			own.drawTile(x, y, t, t == T_NULL || t == T_WALL)
		}
		return count < 108
	}
}