package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_POLZ_H_MIDDLE

class Polz(x: Int, y: Int, len: Int, private val vert: Boolean) : Object(x, y, len, T_POLZ_H_MIDDLE) {
	override fun process(): Boolean {
		return true
	}
}