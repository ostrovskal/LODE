package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.T_PERSON_DROP

class Person(x: Int, y: Int) : Man(x, y, T_PERSON_DROP) {
	override fun process(): Boolean {
		return super.process()
	}
}