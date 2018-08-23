package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants
import ru.ostrovskal.lode.Constants.T_DESTR0
import ru.ostrovskal.lode.Constants.T_WALL
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

class Wall(x: Int, y: Int) : Object(x, y, 1, T_DESTR0) {
	
	init { Sound.playSound(Constants.SND_PERSON_DESTR) }
	
	override fun process(own: ViewGame): Boolean {
		if(count test 1) {
			if(count < 10 || count >= 100) {
				toMap(x, y, if(count > 108) T_WALL else tile)
				tile++
			}
		}
		return count <= 108
	}
}