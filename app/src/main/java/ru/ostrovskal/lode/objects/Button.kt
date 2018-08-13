package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_BRICK
import ru.ostrovskal.lode.Constants.T_BUTTON1
import ru.ostrovskal.lode.tables.Level

class Button(x: Int, y: Int) : Object(x, y, 1, T_BRICK) {
	override fun process(): Boolean {
		tile = if(Level.MAP.personIsRect(x, y - Level.MAP.tileCanvasSize)) T_BUTTON1 else T_BRICK
		Level.MAP.useButton = tile == T_BUTTON1
		drawTile(x, y, tile)
		return true
	}
}