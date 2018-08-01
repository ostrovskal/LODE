package ru.ostrovskal.lode.tables

import com.github.ostrovskal.ssh.sql.Table
import com.github.ostrovskal.ssh.utils.optText
import com.github.ostrovskal.ssh.utils.release
import com.github.ostrovskal.ssh.utils.releaseRun
import ru.ostrovskal.lode.Constants
import ru.ostrovskal.lode.Constants.SYSTEM_DEFAULT

object Pack: Table() {
	@JvmField val id      = integer("_id").notNull().primaryKey()
	@JvmField val name    = text("name").notNull().unique()
	@JvmField val author  = text("author").notNull()
	@JvmField val desc    = text("desc")
	@JvmField val price   = real("price")
	@JvmField val date    = integer("date").notNull()
	@JvmField val levels  = integer("levels").notNull()
	@JvmField val skull   = integer("skull").notNull()
	
	// Возвращает количество уровней
	fun countLevels(nm: String) = select(levels) { where {name eq nm } }.execute()?.releaseRun { integer(0) } ?: 0
	
	// Изменить количество уровней
	fun changeCountLevels(nm: String, isAdd: Boolean) {
		val countPlanets = countLevels(nm) + if(isAdd) 1 else -1
		update { it[levels] = countPlanets; where { name eq nm } }
	}
	// Удалить пустые пакеты, если есть
	// Проверить, что количество уровней в классической системе > 0
	fun check(): Boolean {
		val curDate = System.currentTimeMillis()
		select(name, date).execute()?.release {
			forEach {
				val nm = it.text(name)
				val dt = it.integer(date)
				val count = countLevels(nm)
				// удалить пакет, если в нем нет планет и дата создания отличается от текущей на 100 минут
				if(count <= 0L && (curDate - dt) > 360000) {
					delete { where { name eq nm } }
					if(nm == Constants.KEY_SYSTEM.optText) Constants.KEY_SYSTEM.optText = SYSTEM_DEFAULT
				}
			}
		}
		return countLevels(SYSTEM_DEFAULT) > 0L
	}
	
	// Создание пустой классической системы
	fun default() {
		delete { where { name eq SYSTEM_DEFAULT } }
		insert {
			it[name] = SYSTEM_DEFAULT
			it[date] = System.currentTimeMillis()
			it[author] = "OSTROV"
			it[levels] = 0L
			it[skull] = 8L
			it[desc] = "Основной пакет. После сбора всего золота, открывается доступ к другим пакетам."
			it[price] = 0f
		}
	}
}