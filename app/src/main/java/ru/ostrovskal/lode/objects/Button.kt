package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.views.ViewGame

class Button(x: Int, y: Int) : Object(x, y, 1, T_BUTTON) {
	override fun process(own: ViewGame): Boolean {
		val use = (Level.isIntersectPerson(x, y - SEGMENTS, true) || isProp(x, y - SEGMENTS, FE))
		Level.useButton = use
		own.drawTile(x, y, if(use) T_BUTTON_PRESS else T_WALL)
		return true
	}
}