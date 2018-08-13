package ru.ostrovskal.lode.objects

import android.graphics.Point

abstract class Object(@JvmField var x: Int, @JvmField var y: Int, @JvmField var len: Int = 1, @JvmField var tile: Byte = 0) {
	
	// Обработка объекта
	abstract fun process(): Boolean
	
	// Проверка на видимость
	fun isVisible() = true
	
	// Возврат экранных координат объекта, с учетом сдвига лабиринта
	fun screenCoord() = Point(x, y)
	
	fun drawTile(x: Int, y: Int, tile: Byte) {
	
	}
}
