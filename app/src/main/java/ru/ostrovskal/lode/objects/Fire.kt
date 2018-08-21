package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.utils.flags
import com.github.ostrovskal.ssh.utils.get
import com.github.ostrovskal.ssh.utils.set
import ru.ostrovskal.lode.Constants.SEGMENTS
import ru.ostrovskal.lode.Constants.T_FIRE0
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

class Fire(x: Int, y: Int, length: Int) : Object(x, y, length, T_FIRE0) {
	override fun process(own: ViewGame): Boolean {
		val xx = x / SEGMENTS
		val yy = y / SEGMENTS
		count++
		if(count flags 1) {
			repeat(len) {
				val t = (((Level.buffer[xx + it, yy] - T_FIRE0) + 1) and 3)
				Level.buffer[xx + it, yy] = t + T_FIRE0.toInt()
			}
		}
		return true
	}
}