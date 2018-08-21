package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.utils.flags
import com.github.ostrovskal.ssh.utils.nflags
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

open class Person(x: Int, y: Int, tile: Byte) : Object(x, y, 1, tile) {
	
	// Направление движения
	@JvmField var control                   = 0
	
	// Признак движения влево
	@JvmField protected var isLeft          = false
	
	// Признак движения вправо
	@JvmField protected var isRight         = false
	
	// Признак движения по лестнице
	@JvmField protected var isTrap          = false
	
	// Признак падения
	@JvmField protected var isDrop          = false
	
	// Начальная инициализация
	fun init(xx: Int, yy: Int): Boolean {
		x = xx * SEGMENTS
		y = yy * SEGMENTS
		len = 1
		isDrop = false
		isTrap = false
		isLeft = false
		isRight = false
		count = 0
		return (x >= 0 && y >= 0)
	}
	
	override fun process(own: ViewGame): Boolean {
		val prop = remapProp[getFromMap(x, y)]
		if(prop nflags FP || len > 0) {
			val o = prop and MSKO
			val xx = x
			val yy = y
			
			if(isExact()) {
				if(o == O_GOLD) {
					setToMap(x, y, T_NULL)
					own.processGold()
				}
				else if(o == O_SCORE) {
					setToMap(x, y, T_NULL)
					own.processScore()
				}
				checkDrop(prop)
			}
			if(isDrop) y++
			else {
				if(control flags DIR0) fire()
				if(control flags DIRU) moveUp()
				else if(control flags DIRD) moveDown()
				if(control flags DIRL) moveLeft()
				else if(control flags DIRR) moveRight()
			}
			if(x != xx || y != yy) own.initMap(false)
		}// else len = 0
		render(own)
		return len > 0
	}
	
	private fun fire() {
		if(y % SEGMENTS == 0) {
			val segPos = x % SEGMENTS
			val yy = y + SEGMENTS
			var xx = x - segPos
			// если стоит на краю блока
			if(isRight) {
				if(segPos > 2) xx += SEGMENTS
			} else {
				if(segPos > 0) xx += SEGMENTS
			}
			// x координата прожига
			val dx = xx + if(isRight) SEGMENTS else -SEGMENTS
			// проверки
			if(isProp(dx, y, FF)) {
				if((remapProp[getFromMap(dx, yy)] and MSKO) == O_WALL) {
					Level.pool.add(Wall(dx, yy))
					x = xx
				}
			}
		}
	}
	
	protected fun render(own: ViewGame) {
		val t = tile + if(isTrap) 1 + y % SEGMENTS
		else if(y % SEGMENTS == 0) {
			val frame = x % SEGMENTS
			if(isRight) 5 + frame else if(isLeft) 9 + frame else 0
		} else 0
		own.drawTile(x, y, t.toByte())
	}
	
	protected fun checkDrop(prop: Int) {
		if(prop nflags FT) {
			if(isProp(x, y + SEGMENTS, FD)) {
				isDrop = true
				isTrap = false
				isLeft = false
				isRight = false
			}
			else {
				if(isDrop) count = 15
				isDrop = false
			}
		}
	}
	
	protected fun moveDown(): Boolean {
		if(x % SEGMENTS != 0) return false
		if(y % SEGMENTS == 0) {
			val prop = remapProp[getFromMap(x, y + SEGMENTS)]
			if(prop flags FE) {
				if(!isProp(x, y, FT)) return false
				if(tile != T_PERSON_DROP) return false
			} else if(prop nflags FD && prop nflags FT) return false
		}
		y++
		isRight = false
		isLeft = false
		isTrap = true
		return true
	}
	
	protected fun moveUp(): Boolean {
		if(x % SEGMENTS != 0) return false
		if(y % SEGMENTS == 0) {
			if(!isProp(x, y, FT)) return false
			val prop = remapProp[getFromMap(x, y - SEGMENTS)]
			if(prop flags FE) {
				if(tile != T_PERSON_DROP) return false
			} else if(prop nflags FN) return false
		}
		y--
		isRight = false
		isLeft = false
		isTrap = true
		return true
	}
	
	protected fun moveLeft(): Boolean {
		if(y % SEGMENTS != 0) return false
		if(x % SEGMENTS == 0) {
			val prop = remapProp[getFromMap(x - SEGMENTS, y)]
			if(prop flags FE) {
				if(tile != T_PERSON_DROP) return false
			} else if(prop nflags FN) return false
		}
		x--
		isLeft = true
		isRight = false
		isTrap = false
		return true
	}
	
	protected fun moveRight(): Boolean {
		if(y % SEGMENTS != 0) return false
		if(x % SEGMENTS == 0) {
			val prop = remapProp[getFromMap(x + SEGMENTS, y)]
			if(prop flags FE) {
				if(tile != T_PERSON_DROP) return false
			} else if(prop nflags FN) return false
		}
		x++
		isLeft = false
		isRight = true
		isTrap = false
		return true
	}
}
