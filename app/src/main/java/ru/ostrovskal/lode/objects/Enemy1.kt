package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_ENEMY1_DROP

class Enemy1(x: Int, y: Int) : Man(x, y, T_ENEMY1_DROP) {
	override fun process(): Boolean {
		return true
	}
}