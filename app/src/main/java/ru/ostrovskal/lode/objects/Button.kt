package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

class Button(x: Int, y: Int) : Object(x, y, 1, T_BUTTON1) {
	override fun process(own: ViewGame): Boolean {
		val frame = if(Level.isIntersectPerson(x, y - SEGMENTS) || isProp(x, y - SEGMENTS, FE)) T_BUTTON1 else T_BUTTON0
		Level.useButton = frame == T_BUTTON1
		setToMap(x, y, frame)
		return true
	}
}