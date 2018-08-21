package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_BUBBLE0
import ru.ostrovskal.lode.views.ViewGame

class Bubble(x: Int, y: Int) : Object(x, y, 1, T_BUBBLE0) {
	override fun process(own: ViewGame): Boolean {
		return true
	}
}