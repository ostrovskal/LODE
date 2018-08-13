package ru.ostrovskal.lode.objects

class Platform(x: Int, y: Int, length: Int, private val vert: Boolean) : Object(x, y, length) {
	override fun process(): Boolean {
		return true
	}
}