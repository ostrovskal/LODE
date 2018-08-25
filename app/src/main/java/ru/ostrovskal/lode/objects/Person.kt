package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.utils.dirHorz
import com.github.ostrovskal.ssh.utils.ntest
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.findBox
import ru.ostrovskal.lode.tables.Level.fromMap
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.views.ViewGame

open class Person(x: Int, y: Int, tile: Byte) : Object(x, y, 1, tile) {

	// Смещение тайла относительно базового
	@JvmField protected var tx          = 0
	private var tx1                     = 0
	
	// Начальная инициализация
	fun init(xx: Int, yy: Int): Boolean {
		x = xx * SEGMENTS
		y = yy * SEGMENTS
		len = 1
		control = 0
		count = 0
		tx = 0
		tx1 = 0
		return (x >= 0 && y >= 0)
	}
	
	override fun process(own: ViewGame): Boolean {
		if(own.nStart == 0) {
			val prop = remapProp[fromMap(x, y)]
			if(prop ntest FP || len > 0) {
				val o = prop and MSKO
				val xx = x
				val yy = y
				
				if(isExact()) {
					if(o == O_GOLD || o == O_SCORE) own.processSpec(x, y, o)
				}
				checkDrop(prop)
				if(control test MODE_DROP) y++
				else {
					val c = own.cursor.buttonStates()
					if(c test DIR0) fire()
					if(c test DIRU) moveUp(prop)
					else if(c test DIRD) moveDown(prop)
					if(c test DIRL) moveLeft(prop)
					else if(c test DIRR) moveRight()
				}
				if(x != xx || y != yy) own.prepareMap(false, true)
			}
			else len = 0
		}
		if(own.nStart ntest 4) own.drawTile(x, y, (tile + tx).toByte())
		return len > 0
	}
	
	private fun fire() {
		if(y % SEGMENTS == 0) {
			val segPos = x % SEGMENTS
			val yy = y + SEGMENTS
			var xx = x - segPos
			var dx = if(control test MODE_RIGHT) SEGMENTS else -SEGMENTS
			// если стоит на краю блока
			xx += if(dx == SEGMENTS) {
				if(segPos > 2) SEGMENTS else 0
			} else {
				if(segPos > 0) SEGMENTS else 0
			}
			// проверки
			dx += xx
			if(isProp(dx, y, FF)) {
				if((remapProp[fromMap(dx, yy)] and MSKO) == O_WALL) {
					Level.pool.add(Wall(dx, yy))
					x = xx
				}
			}
		}
	}
	
	protected fun checkDrop(prop: Int) {
		if(y % SEGMENTS == 0) {
			if(control test MODE_DROP) {
				count = 15
				control = control and MODE_DROP.inv()
			}
			if(prop ntest FT) {
				val xx = x % SEGMENTS > 0
				if(isProp(x, y + SEGMENTS, FD)) {
					var isD = true
					if(xx) isD = isProp(x + SEGMENTS, y + SEGMENTS, FD)
					if(isD) {
						control = (control and (DIRH or DIRV)) or MODE_DROP
						tx = 0
					}
				}
			} else if(prop test FV) tx = 11
		}
	}
	
	protected fun moveDown(prop: Int): Boolean {
		if(x % SEGMENTS != 0 && prop ntest FV) return false
		if(y % SEGMENTS == 0) {
			val p = remapProp[fromMap(x, y + SEGMENTS)]
			if(p test FE) {
				if(prop ntest FT || tile != T_PERSON_DROP) return false
			}
			else if(p ntest (FD or FT)) return false
			if(prop test FV) {
				tx = 0
				y++
				control = (control and (DIRV or DIRH)) or MODE_DROP
				return true
			}
		}
		y++
		tx = if(tx == 1) 2 else 1
		return true
	}
	
	protected fun moveUp(prop: Int): Boolean {
		if(x % SEGMENTS != 0) return false
		if(y % SEGMENTS == 0) {
			val p = remapProp[fromMap(x, y - SEGMENTS)]
			if(prop ntest FT || prop test FV) return false
			if(p test (FN or FD or FE)) {
				if(p test FE && tile != T_PERSON_DROP) return false
			} else return false
		}
		tx = if(prop ntest FT) {
			if(control test MODE_RIGHT) 5 else 9
		} else {
			if(tx == 1) 2 else 1
		}
		y--
		return true
	}
	
	protected fun moveLeft(prop: Int): Boolean {
		if(y % SEGMENTS != 0) return false
		val p = remapProp[fromMap(x - SEGMENTS, y)]
		if(x % SEGMENTS == 0) {
			val isZomby = tile != T_PERSON_DROP
			if(p test (FE or FN)) {
				if(p test FE && isZomby) return false
			} else {
				if(p test FX && !isZomby) moveBox(DIRL)
				return false
			}
		}
		tx1++
		control = (control and (DIRV or DIRH)) or MODE_LEFT
		tx = (if(prop test FV) 15 else 7) + (tx1 % 3)
		x -= offsPerson[tx]
		return true
	}
	
	protected fun moveRight(): Boolean {
		if(y % SEGMENTS != 0) return false
		val p = remapProp[fromMap(x + SEGMENTS, y)]
		if(x % SEGMENTS == 0) {
			val isZomby = tile != T_PERSON_DROP
			if(p test (FE or FN)) {
				if(p test FE && isZomby) return false
			} else {
				if(p test FX && !isZomby) moveBox(DIRR)
				return false
			}
		}
		tx1++
		control = (control and (DIRV or DIRH)) or MODE_RIGHT
		tx = (if(p test FV) 11 else 3) + (tx1 % 3)
		x += offsPerson[tx]
		return true
	}
	
	private fun moveBox(dir: Int) {
		val d = dir.dirHorz * SEGMENTS
		findBox(x +  d / 2, y)?.apply {
			if(!isProp(x + d, y, FB)) {
				x += d
				setMap(x - d, y)
			}
		}
	}
	
}
