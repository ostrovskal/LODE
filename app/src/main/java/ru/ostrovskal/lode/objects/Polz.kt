package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

class Polz(x: Int, y: Int, len: Int, vert: Boolean) : Platform(x, y, len, vert) {
	override fun process(own: ViewGame): Boolean {
		val use = Level.useButton
		Level.useButton = true
		val res = super.process(own)
		Level.useButton = use
		return res
	}
}