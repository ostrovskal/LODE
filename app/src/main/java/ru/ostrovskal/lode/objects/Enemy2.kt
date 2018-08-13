package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_ENEMY2_DROP

class Enemy2(x: Int, y: Int) : Man(x, y, T_ENEMY2_DROP) {
	override fun process(): Boolean {
		return true
	}
}