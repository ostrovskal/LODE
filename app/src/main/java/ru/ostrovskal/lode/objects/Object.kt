@file:Suppress("NOTHING_TO_INLINE")

package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.SEGMENTS
import ru.ostrovskal.lode.views.ViewGame

open class Object(@JvmField var x: Int, @JvmField var y: Int, @JvmField var len: Int, @JvmField protected var tile: Byte) {
	
	// Направление движения
	@JvmField var control       = 0
	
	// Счетчик
	@JvmField var count         = 0

	// Обработка объекта
	open fun process(own: ViewGame): Boolean = false
	
	// Проверка на точность координат объекта
	inline fun isExact() = (x % SEGMENTS == 0) && (y % SEGMENTS == 0)
	
}
