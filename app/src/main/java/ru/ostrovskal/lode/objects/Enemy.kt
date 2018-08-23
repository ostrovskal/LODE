package ru.ostrovskal.lode.objects

import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.utils.dirHorz
import com.github.ostrovskal.ssh.utils.get
import com.github.ostrovskal.ssh.utils.ntest
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.fromMap
import ru.ostrovskal.lode.tables.Level.isProp
import ru.ostrovskal.lode.tables.Level.toMap
import ru.ostrovskal.lode.views.ViewGame

open class Enemy(x: Int, y: Int, tile: Byte) : Person(x, y, tile) {
	
	// Координаты на которые наводится
	private var px              = 0
	private var py              = 0
	
	private fun changeDirection() {
		px = Level.person.x
		py = Level.person.y
		control = if(x > px) DIRL else DIRR
		control = control or if(y > py) DIRU else if(y < py) DIRD else DIRN
	}
	
	private fun moving() {
		if(control test DIRU) {
			if(moveUp()) {
				if(y == py && y % SEGMENTS == 0) {
					if(!isProp(x + control.dirHorz * SEGMENTS, y, FN)) {
						changeDirection()
						control = control and DIRU.inv()
					}
				}
				return
			}
		}
		else if(control test DIRD) {
			if(moveDown()) {
				if(y == py && y % SEGMENTS == 0) {
					if(!isProp(x + control.dirHorz * SEGMENTS, y, FN)) {
						changeDirection()
						control = control and DIRD.inv()
					}
				}
				return
			}
		}
		if(control test DIRL) {
			if(!moveLeft()) control = control xor DIRH
		}
		else if(control test DIRR) {
			if(!moveRight()) control = control xor DIRH
		}
	}
	
	override fun process(own: ViewGame): Boolean {
		if(!own.isDead) {
			val prop = remapProp[fromMap(x, y)]
			toMap(x, y, MSKT.toByte(), OPS_AND)
			if(prop test FA) {
				// проверить что убило(огонь, прожиг(ENEMY1, ENEMY2), задавило платформой(BETON))
				val o = prop and MSKO
				when {
					prop test FZ  -> own.addScore(O_BETON)
					o == O_ZARAST -> own.addScore(if(tile == T_ENEMY1_DROP) O_ENEMY1 else O_ENEMY1)
					o == O_FIRE   -> own.addScore(O_FIRE)
				}
				Sound.playSound(SND_ENEMY_DEAD)
				// запустить десант(только для красных), случайно выбирая позицию
				if(tile == T_ENEMY1_DROP) {
					count = 0
					repeat(Level.width) {
						if(remapProp[Level.buffer[it, 0]] test (FN or FD)) count++
					}
					if(count > 0) {
						count = Level.rnd.nextInt(count)
						repeat(Level.width) {
							if(remapProp[Level.buffer[it, 0]] test (FN or FD)) {
								count--
								if(count == 0) return init(it, 0)
							}
						}
					}
				}
				return false
			}
			if(isExact()) {
				if(count ntest 31) changeDirection()
				checkDrop(prop)
			}
			if(count test 1) {
				if(control test MODE_DROP) y++ else moving()
			}
			toMap(x, y, MSKE.toByte(), OPS_OR)
		}
		render(own)
		return true
	}
}

class Enemy1(x: Int, y: Int) : Enemy(x, y, T_ENEMY1_DROP)

class Enemy2(x: Int, y: Int) : Enemy(x, y, T_ENEMY2_DROP)
