package ru.ostrovskal.lode.objects

import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Level.fromMap
import ru.ostrovskal.lode.views.ViewGame

class Fire(x: Int, y: Int, length: Int) : Object(x, y, length, T_FIRE0) {
	override fun process(own: ViewGame): Boolean {
		repeat(len) {
			val t = if(fromMap(x + it * SEGMENTS, y - SEGMENTS) and MSKT == O_FIRE) {
				showLavaTiles[((it and 3) + ((count shr 1) and 3))]
			} else {
				if(Level.rnd.nextInt(50) == 1)
					Level.pool.add(Bubble(x + it * SEGMENTS + Level.rnd.nextInt(SEGMENTS), y - SEGMENTS / 2))
				showFireTiles[((it and 7) + ((count shr 1)) and 7)]
			}
			own.drawTile(x + it * SEGMENTS, y, t)
		}
		return true
	}
}