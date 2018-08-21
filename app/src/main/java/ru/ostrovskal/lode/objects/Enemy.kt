package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.utils.flags
import com.github.ostrovskal.ssh.utils.get
import com.github.ostrovskal.ssh.utils.nflags
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewGame

open class Enemy(x: Int, y: Int, tile: Byte) : Person(x, y, tile) {
	
	// Координаты на которые наводится
	private var px              = 0
	private var py              = 0
	
	private fun changeDirection() {
		px = Level.person.x
		py = Level.person.y
		control = if(x > px) Constants.DIRL else Constants.DIRR
		control = control or if(y > py) Constants.DIRU else if(y < py) Constants.DIRD else Constants.DIRN
	}
	
	private fun moving() {
		if(control flags DIRU) {
			if(moveUp()) {
				if(y == py && y % SEGMENTS == 0) {
					if(!isProp(x + if(control flags DIRR) SEGMENTS else -SEGMENTS, y, FN)) {
						changeDirection()
						control = control xor DIRU
					}
				}
				return
			}
		}
		else if(control flags DIRD) {
			if(moveDown()) {
				if(y == py && y % SEGMENTS == 0) {
					if(!isProp(x + if(control flags DIRR) SEGMENTS else -SEGMENTS, y, FN)) {
						changeDirection()
						control = control xor DIRD
					}
				}
				return
			}
		}
		if(control flags DIRL) {
			if(!moveLeft()) control = control xor (DIRL or DIRR)
		}
		else if(control flags DIRR) {
			if(!moveRight()) control = control xor (DIRL or DIRR)
		}
	}
	
	override fun process(own: ViewGame): Boolean {
		if(!own.isDead) {
			val v = getFromMap(x, y)
			val prop = remapProp[v]
			setToMap(x, y, (v and MSKT).toByte())
			if(prop flags FA) {
				// проверить что убило(огонь, прожиг(ENEMY1, ENEMY2), задавило платформой(BETON))
				val o = prop and MSKO
				when {
					prop flags FZ -> own.addScore(O_BETON)
					o == O_ZARAST -> own.addScore(if(tile == T_ENEMY1_DROP) O_ENEMY1 else O_ENEMY1)
					o == O_FIRE   -> {
						own.addScore(O_FIRE)
						Sound.playSound(SND_OBJECT_TO_FIRE)
					}
				}
				// запустить десант(только для красных), случайно выбирая позицию
				if(tile == T_ENEMY1_DROP) {
					count = 0
					repeat(Level.width) {
						if(remapProp[Level.buffer[it, 0]] flags (FN or FD)) count++
					}
					if(count > 0) {
						count = Level.rnd.nextInt(count)
						repeat(Level.width) {
							if(remapProp[Level.buffer[it, 0]] flags (FN or FD)) {
								count--
								if(count == 0) return init(it, 0)
						}
					}
				}
				}
				return false
			}
			count++
			if(isExact()) {
				if(count nflags 31) changeDirection()
				checkDrop(prop)
			}
			if(count flags 1) {
				if(isDrop) y++ else moving()
			}
			setToMap(x, y, (getFromMap(x, y) or MSKE).toByte())
		}
		render(own)
		return true
	}
}