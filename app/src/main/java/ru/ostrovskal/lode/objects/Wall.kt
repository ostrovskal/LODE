package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.utils.flags
import ru.ostrovskal.lode.Constants
import ru.ostrovskal.lode.Constants.T_DESTR0
import ru.ostrovskal.lode.Constants.T_WALL
import ru.ostrovskal.lode.views.ViewGame

class Wall(x: Int, y: Int) : Object(x, y, 1, T_DESTR0) {
	override fun process(own: ViewGame): Boolean {
		if(count == 0) Sound.playSound(Constants.SND_PERSON_DESTR)
		count++
		if(count flags 1) {
			if(count < 10 || count >= 100) {
				val t = if(count > 108) T_WALL else tile
				setToMap(x, y, t)
				tile++
			}
		}
		return count <= 108
	}
}