package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_FIRE0

class Fire(x: Int, y: Int, length: Int) : Object(x, y, length, T_FIRE0) {
	override fun process(): Boolean {
		return true
	}
}