package ru.ostrovskal.lode.objects

open class Man(x: Int, y: Int, tile: Byte) : Object(x, y, 1, tile) {
	override fun process(): Boolean {
		return true
	}
}