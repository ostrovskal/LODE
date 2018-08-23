package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.utils.info
import com.github.ostrovskal.ssh.utils.ntest
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.arr
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.fromMap
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.views.ViewGame

open class Person(x: Int, y: Int, tile: Byte) : Object(x, y, 1, tile) {
	
	// Начальная инициализация
	fun init(xx: Int, yy: Int): Boolean {
		x = xx * SEGMENTS
		y = yy * SEGMENTS
		len = 1
		control = 0
		count = 0
		return (x >= 0 && y >= 0)
	}
	
	override fun process(own: ViewGame): Boolean {
		val prop = remapProp[fromMap(x, y)]
		if(prop ntest FP || len > 0) {
			val o = prop and MSKO
			val xx = x
			val yy = y
			
			if(isExact()) {
				if(o == O_GOLD || o == O_SCORE) own.processSpec(x, y, o)
				checkDrop(prop)
			}
			if(control test MODE_DROP) y++
			else {
				val c = own.cursor.buttonStates()
				if(c test DIR0) fire()
				if(c test DIRU) moveUp()
				else if(c test DIRD) moveDown()
				if(c test DIRL) moveLeft()
				else if(c test DIRR) moveRight()
			}
			if(x != xx || y != yy) own.prepareMap(false, true)
		}// else len = 0
		render(own)
		return len > 0
	}
	
	private fun fire() {
		if(y % SEGMENTS == 0 && control ntest MODE_DROP) {
			val segPos = x % SEGMENTS
			val yy = y + SEGMENTS
			var xx = x - segPos
			// если стоит на краю блока
			if(control test MODE_RIGHT) {
				if(segPos > 2) xx += SEGMENTS
			} else {
				if(segPos > 0) xx += SEGMENTS
			}
			// x координата прожига
			val dx = xx + if(control test MODE_RIGHT) SEGMENTS else - SEGMENTS
			// проверки
			fromMap(dx, y).arr.info()
			fromMap(dx, y + SEGMENTS).arr.info()
			if(isProp(dx, y, FF)) {
				if((remapProp[fromMap(dx, yy)] and MSKO) == O_WALL) {
					Level.pool.add(Wall(dx, yy))
					x = xx
				}
			}
		}
	}
	
	protected fun render(own: ViewGame) {
		val isY = y % SEGMENTS == 0
		val t = tile + if(control test MODE_TRAP) {
			val trap = (1 + y % SEGMENTS)
			if(isY) {
				if(isProp(x, y, FT)) trap else 0
			} else trap
		} else if(isY) {
			val frame = x % SEGMENTS
			if(control test MODE_RIGHT) 5 + frame else if(control test MODE_LEFT) 9 + frame else 0
		} else 0
		own.drawTile(x, y, t.toByte())
	}
	
	protected fun checkDrop(prop: Int) {
		if(prop ntest FT) {
			if(isProp(x, y + SEGMENTS, FD)) control = (control and (DIRH or DIRV)) or MODE_DROP
			else {
				if(control test MODE_DROP) {
					count = 15
					control = control and MODE_DROP.inv()
				}
			}
		}
	}
	
	protected fun moveDown(): Boolean {
		if(x % SEGMENTS != 0) return false
		if(y % SEGMENTS == 0) {
			val prop = remapProp[fromMap(x, y + SEGMENTS)]
			if(prop test FE) {
				if(!isProp(x, y, FT)) return false
				if(tile != T_PERSON_DROP) return false
			} else if(prop ntest FD && prop ntest FT) return false
		}
		y++
		control = (control and (DIRH or DIRV)) or MODE_TRAP
		return true
	}
	
	protected fun moveUp(): Boolean {
		if(x % SEGMENTS != 0) return false
		if(y % SEGMENTS == 0) {
			if(!isProp(x, y, FT)) return false
			val prop = remapProp[fromMap(x, y - SEGMENTS)]
			if(prop test FE) {
				if(tile != T_PERSON_DROP) return false
			} else if(prop ntest FN) return false
		}
		y--
		control = (control and (DIRH or DIRV)) or MODE_TRAP
		return true
	}
	
	protected fun moveLeft(): Boolean {
		if(y % SEGMENTS != 0) return false
		if(x % SEGMENTS == 0) {
			val prop = remapProp[fromMap(x - SEGMENTS, y)]
			if(prop test FE) {
				if(tile != T_PERSON_DROP) return false
			} else if(prop ntest FN) return false
		}
		x--
		control = (control and (DIRH or DIRV)) or MODE_LEFT
		return true
	}
	
	protected fun moveRight(): Boolean {
		if(y % SEGMENTS != 0) return false
		if(x % SEGMENTS == 0) {
			val prop = remapProp[fromMap(x + SEGMENTS, y)]
			if(prop test FE) {
				if(tile != T_PERSON_DROP) return false
			} else if(prop ntest FN) return false
		}
		x++
		control = (control and (DIRH or DIRV)) or MODE_RIGHT
		return true
	}
}
