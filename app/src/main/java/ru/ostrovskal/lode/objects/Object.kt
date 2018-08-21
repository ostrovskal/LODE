@file:Suppress("NOTHING_TO_INLINE")

package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.utils.flags
import com.github.ostrovskal.ssh.utils.get
import com.github.ostrovskal.ssh.utils.set
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

open class Object(@JvmField var x: Int, @JvmField var y: Int, @JvmField var len: Int, @JvmField protected var tile: Byte) {

	// Счетчик
	protected var count         = 0

	// Обработка объекта
	open fun process(own: ViewGame): Boolean = false
	
	// Проверка на свойства элемента по блочным координатам на карте
	fun isPropExact(x: Int, y: Int, props: Int): Boolean {
		val v = if(x < 0 || y < 0 || x >= Level.width || y >= Level.height) T_BETON.toInt() else Level.buffer[x, y]
		return remapProp[v] flags props
	}
	
	// Проверка на точность координат объекта
	inline fun isExact() = (x % SEGMENTS == 0) && (y % SEGMENTS == 0)

	// Проверка на свойства элемента по определенным координатам на карте
	fun isProp(x: Int, y: Int, props: Int) = (remapProp[getFromMap(x, y)] flags props)
	
	fun setToMap(x: Int, y: Int, t: Byte) {
		if(y < Level.mapSegments.h && x < Level.mapSegments.w && y >= 0 && x >= 0)
			Level.buffer[x / SEGMENTS, y / SEGMENTS] = t
	}
	
	fun getFromMap(x: Int, y: Int) = if(y >= Level.mapSegments.h || x >= Level.mapSegments.w || y < 0 || x < 0) {
		T_BETON.toInt()
	} else {
		Level.buffer[x / SEGMENTS, y / SEGMENTS]
	}
}
