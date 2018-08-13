@file:Suppress("NOTHING_TO_INLINE")

package ru.ostrovskal.lode.tables

import android.content.Context
import android.graphics.*
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.SqlField
import com.github.ostrovskal.ssh.sql.RuleOption
import com.github.ostrovskal.ssh.sql.Table
import com.github.ostrovskal.ssh.utils.*
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.objects.*
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

object Level: Table() {
	@JvmField val id        = integer("_id").notNull().primaryKey()
	@JvmField val system    = text("system").notNull().references(Pack.name, RuleOption.CASCADE, RuleOption.CASCADE)
	@JvmField val creator   = text("creator").notNull().default("OSTROV")
	@JvmField val position  = integer("position").notNull().checked { it.between(0, 101) }
	@JvmField val target    = integer("target").notNull()
	@JvmField val blocked   = integer("blocked").notNull().default(1)
	@JvmField val content   = blob("content").notNull()
	@JvmField val create    = integer("create").notNull()
	
	object MAP {
		@SqlField("position") @JvmField var num     = 0L
		@SqlField("target") @JvmField var gold      = 0
		@SqlField("create") @JvmField var date      = 0L
		@SqlField("blocked") @JvmField var block    = true
		@SqlField("content") @JvmField var buffer   = byteArrayOf(0, 0)
		@SqlField("creator") @JvmField var auth     = ""
		@SqlField("system") @JvmField var pack      = ""
		
		@JvmField val pool              = mutableListOf<Object>()
		@JvmField val pointPeson        = Point(-1, -1)
		@JvmField var useButton         = false
		// размер спрайта на экране
		@JvmField var tileCanvasSize 	= 0
		
		val width               get()   = buffer[0].toInt()
		val height              get()   = buffer[1].toInt()
		
		inline fun personNull() { pointPeson.set(-1, -1) }
		
		inline fun personIsNull() = ( pointPeson.x == -1 || pointPeson.y == -1 )
		
		inline fun personPos() = pointPeson
		
		inline fun personPos(x: Int, y: Int) { pointPeson.set(x, y) }

		fun personIsRect(x: Int, y: Int): Boolean {
			val xx = Math.abs(x - pointPeson.x)
			val yy = Math.abs(y - pointPeson.y)
			val sz = tileCanvasSize / 2
			return xx < sz && yy < sz
		}
		
		// Сохранить уровень
		private fun save() {
			val sb = StringBuilder(width * height + 10 * 6)
			// запомнить автор, габариты
			sb.append("$auth\n").append("$width\n").append("$height\n")
			// перекодировка
			for(y in 0 until height) {
				for(x in 0 until width) sb.append(charsOfLevelMap[buffer[x, y]])
				sb.append('\n')
			}
			// запись
			makeDirectories("levels/$pack", Constants.FOLDER_FILES, "$num.lv").writeText(sb.toString())
		}
		
		// Создать превью уровня
		private fun preview(context: Context, blockSize: Int): Bitmap? {
			val dst = Rect()
			val src = Rect()
			val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
			// определяем максимальный габарит
			val maxWidth = if(width > height) width else height
			val sz = blockSize * maxWidth
			// выравнивание по центру
			val offsX = (sz - blockSize * width) / 2
			val offsY = (sz - blockSize * height) / 2
			// создаем битмапы
			val tiles = context.createBitmap(R.drawable.sprites) ?: return null
			val min = Bitmap.createBitmap(sz, sz, Bitmap.Config.ARGB_8888) ?: return null
			val canvas = Canvas(min)
			val cols = 10
			val wh = tiles.width / cols
			// стираем превью
			canvas.drawColor(0)
			// нарисовать в битмап всю планету
			for(y in 0 until height) {
				dst.top = offsY + y * blockSize
				dst.bottom = dst.top + blockSize
				for(x in 0 until width) {
					val v = remapTiles[buffer[x, y]]
					src.left = v % cols * wh
					src.top = v / cols * wh
					src.right = src.left + wh
					src.bottom = src.top + wh
					dst.left = offsX + x * blockSize
					dst.right = dst.left + blockSize
					canvas.drawBitmap(tiles, src, dst, mPaint)
				}
			}
			tiles.recycle()
			return min
		}
		
		// Создать миниатюру
		private fun miniature(context: Context)
		{
			val bmp = preview(context, BLOCK_MINIATURE) ?: return
			FileOutputStream(makeDirectories("miniatures/$pack", Constants.FOLDER_FILES, "$num.png")).release {
				bmp.compress(Bitmap.CompressFormat.PNG, 100, this)
			}
			bmp.recycle()
		}
		
		// Создать классический уровень из папки активов
		fun make(context: Context, pck: String, num: Int) = try {
			var width = 0
			var idx = 0
			// загрузка
			context.assets.open("$pck/$num.lv").reader().release {
				forEachLine {
					val v = it.ival(0, 10)
					when(idx++) {
						0    -> auth = it
						1    -> width = v
						2    -> { buffer = ByteArray(width * v + 2); buffer[0] = width.toByte(); buffer[1] = v.toByte() }
						else -> {
							val row = idx - 4
							for(col in 0 until width) {
								buffer[col, row] = charsOfLevelMap.search(it[col], T_NULL.toInt())
							}
						}
					}
				}
			}
			pack = pck
			true
		} catch(e: IOException) {
			reset()
			false
		}
		
		// Добавить или обновить уровень в БД
		fun store(context: Context): Boolean {
			if(!setEntities(false)) {
				context.resources.getString(R.string.person_not_found, num).debug()
				return false
			}
			miniature(context)
			save()
			val result: Boolean
			if(exist { system.eq(pack) and position.eq(num) } ) {
				result = update {
					autoValues(this@MAP)
					where { position.eq(this@MAP.num) and system.eq(pack) }
				} > 0L
			} else {
				date = System.currentTimeMillis()
				if(auth.isEmpty()) auth = KEY_PLAYER.optText
				result = insert { it.autoValues(this@MAP) } > 0L
				if(result) Pack.changeCountLevels(pack, true)
			}
			return result
		}
		
		// Удалить уровень
		fun delete(isReset: Boolean): Boolean {
			// удалить из БД
			if(delete { where { system.eq(pack) and position.eq(num) } } > 0L) {
				// Изменить количество уровней в системе
				Pack.changeCountLevels(pack, false)
				// Сжать номера
				select(Level.position) {
					where { Level.system.eq(pack) and Level.position.greater(num) }
					orderBy(Level.position, true)
				}.execute()?.release {
					forEach {
						val n = integer(0)
						update {
							it[Level.position] = n - 1
							where { Level.system.eq(pack) and Level.position.eq(n) }
						}
					}
				}
				if(isReset) {
					// удалить текстовое представление карты
					makeDirectories("levels/$pack", Constants.FOLDER_FILES, "$num.lv").delete()
					// удалить миниатюру
					makeDirectories("miniatures/$pack", Constants.FOLDER_FILES, "$num.png").delete()
					// сброс
					reset()
				}
				return true
			}
			return false
		}
		
		// Загрузить из БД
		fun load(pos: Int): Boolean {
			select { where { Level.position.eq(pos.toLong()) and Level.system.eq(pack) } }.execute()?.release {
				autoValues(this@MAP)
				setEntities(true)
				return true
			}
			reset()
			return false
		}
		
		// Установить все объекты уровня
		private fun setEntities(isGame: Boolean): Boolean {
			pool.clear()
			personNull()
			gold = 0
			for(y in 0 until height) {
				var o = remapProp[buffer[0, y]] and MSKO
				var len = 1
				for(x in 0 until width) {
					val prop = remapProp[buffer[x, y]]
					val isLen = prop nflags FL
					val cur = prop and MSKO
					if(cur != o || isLen) {
						when(o) {
							O_PERSON        -> { personPos(x, y); if(isGame) buffer[x, y] = T_NULL.toInt() }
							O_ENEMY1        -> pool.add(Enemy1(x, y))
							O_ENEMY2        -> pool.add(Enemy2(x, y))
							O_BUTTON        -> pool.add(Button(x, y))
							O_FIRE          -> pool.add(Fire(x, y, len))
							O_PLATFORM_HORZ -> pool.add(Platform(x, y, len, false))
							O_PLATFORM_VERT -> pool.add(Platform(x, y, len, true))
							O_POLZ_HORZ     -> pool.add(Polz(x, y, len, false))
							O_POLZ_VERT     -> pool.add(Polz(x, y, len, true))
							O_GOLD          -> gold++
						}
						len = 0
					}
					o = cur
					len++
				}
			}
			if(personIsNull()) return false
			pool.add(0, Person(pointPeson.x, pointPeson.y))
			return true
		}
		
		// Сбросить в исходное состояние
		private fun reset() {
			personNull()
			auth = ""
			block = true
			num = -1; gold = 0
			buffer = byteArrayOf(0, 0)
			pool.clear()
		}
	}
	
	// Создать уровни классического пакета
	fun default(context: Context) {
		// запустить в отдельном потоке
		thread {
			delete { where { system eq SYSTEM_DEFAULT } }
			// проверить содержимое папки assets/classic
			val classic = context.assets.list(SYSTEM_DEFAULT)
//			if(classic.size < 30) error("System of assets\\$SYSTEM_DEFAULT not found!")
			classic.forEach {
				var res = Level.MAP.make(context, SYSTEM_DEFAULT, it.substring(0, it.lastIndexOf('.')).toInt())
				if(res) res = Level.MAP.store(context)
				if(!res) context.resources.getString(R.string.error_make_or_save, Level.MAP.num).debug()
			}
		}
	}
}