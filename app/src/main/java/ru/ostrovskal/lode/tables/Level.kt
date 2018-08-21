@file:Suppress("NOTHING_TO_INLINE")

package ru.ostrovskal.lode.tables

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Rand
import com.github.ostrovskal.ssh.SqlField
import com.github.ostrovskal.ssh.sql.RuleOption
import com.github.ostrovskal.ssh.sql.Table
import com.github.ostrovskal.ssh.utils.*
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.objects.*
import java.io.FileOutputStream
import java.io.IOException

object Level: Table() {
	@JvmField val id        = integer("_id").notNull().primaryKey()
	@JvmField val system    = text("system").notNull().references(Pack.name, RuleOption.CASCADE, RuleOption.CASCADE)
	@JvmField val creator   = text("creator").notNull().default("OSTROV")
	@JvmField val position  = integer("position").notNull().checked { it.between(0, 101) }
	@JvmField val blocked   = integer("blocked").notNull().default(1)
	@JvmField val content   = blob("content").notNull()
	@JvmField val create    = integer("create").notNull()
	
	@SqlField("position")   @JvmField var num     = 0L
	@SqlField("create")     @JvmField var date    = 0L
	@SqlField("blocked")    @JvmField var block   = true
	@SqlField("content")    @JvmField var buffer  = byteArrayOf(0, 0)
	@SqlField("creator")    @JvmField var auth    = ""
	@SqlField("system")     @JvmField var pack    = ""
	
	// Пул объектов
	@JvmField val pool              = mutableListOf<Object>()
	
	// Количество золота на уровне
	@JvmField var gold              = 0
	
	// Псевдослучайная величина
	@JvmField val rnd               = Rand()

	// Человек
	@JvmField val person            = Person(-1, -1, T_PERSON_DROP)

	// Признак нажатой кнопки
	@JvmField var useButton         = false

	// Размер карты в сегментах
	@JvmField var mapSegments       = Size()
	
	val width                       get()   = buffer[0].toInt()
	val height                      get()   = buffer[1].toInt()
	
	fun isIntersectPerson(x: Int, y: Int): Boolean {
		val xx = Math.abs(x - person.x)
		val yy = Math.abs(y - person.y)
		val sz = SEGMENTS / 2
		return xx <= sz && yy <= sz
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
		val wh = tiles.width / 10
		// стираем превью
		canvas.drawColor(0)
		// нарисовать в битмап всю планету
		repeat(height) {y ->
			dst.top = offsY + y * blockSize
			dst.bottom = dst.top + blockSize
			repeat(width) {x ->
				val v = remapTiles[buffer[x, y]]
				src.left = v % 10 * wh
				src.top = v / 10 * wh
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
	
	// Сохранить уровень
	private fun save() {
		val sb = StringBuilder((width + 1) * height + auth.length + 1)
		// запомнить автора
		sb.append("$auth\n")
		// перекодировка
		repeat(height) { y ->
			repeat(width) { sb.append(charsOfLevelMap[remapProp[buffer[it, y] and MSKT] and MSKO]) }
			sb.append('\n')
		}
		// запись
		makeDirectories("levels/$pack", Constants.FOLDER_FILES, "$num.lv").writeText(sb.toString())
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
				autoValues(Level)
				where { position.eq(num) and system.eq(pack) }
			} > 0L
		} else {
			date = System.currentTimeMillis()
			if(auth.isEmpty()) auth = KEY_PLAYER.optText
			result = insert { it.autoValues(Level) } > 0L
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
				forEach {_ ->
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
	fun load(pos: Int, isClearObj: Boolean): Boolean {
		select { where { Level.position.eq(pos.toLong()) and Level.system.eq(pack) } }.execute()?.release {
			autoValues(Level)
			if(setEntities(isClearObj))
				return true
		}
		reset()
		return false
	}
	
	// Установить все объекты уровня
	private fun setEntities(isGame: Boolean): Boolean {
		fun setObj(o: Int, x: Int, y: Int, len: Int) {
			if(len > 0) {
				val xx = (x - len) * SEGMENTS
				val yy = y * SEGMENTS
				when(o) {
					O_PERSON -> { person.x = x - len; person.y = y}
					O_ENEMY1 -> pool.add(0, Enemy1(xx, yy))
					O_ENEMY2 -> pool.add(0, Enemy2(xx, yy))
					O_POLZH  -> if(len > 1) pool.add(Polz(xx, yy, len, false)) else "Горизонтальный ползунок {$xx, $yy} имеет недопустимую длину $len".debug()
					O_POLZV  -> if(len > 1) pool.add(Polz(xx, yy, len, true)) else "Вертикальный ползунок {$xx, $yy} имеет недопустимую длину $len".debug()
					O_BUTTON -> pool.add(Button(xx, yy))
					O_FIRE   -> pool.add(Fire(xx, yy, len))
					O_PLATH  -> pool.add(Platform(xx, yy, len, false))
					O_PLATV  -> pool.add(Platform(xx, yy, len, true))
					O_GOLD   -> gold += len
				}
				//"${o.obj} $len ${xx / SEGMENTS} ${yy / SEGMENTS}".info()
				if(o >= O_PLATH && isGame) {
					repeat(len) { buffer[x - it - 1, y] = T_NULL.toInt() }
				}
			}
		}
		pool.clear()
		person.len = 0
		person.x = -1
		gold = 0
		repeat(height) {y ->
			var o = remapProp[buffer[0, y]] and MSKO
			var len = 0
			repeat(width) { x ->
				val prop = remapProp[buffer[x, y]]
				val isLen = prop nflags FL
				val cur = prop and MSKO
				if(cur != o || isLen) {
					setObj(o, x, y, len)
					len = 0
				}
				o = cur
				len++
			}
			setObj(o, width, y, len)
		}
		return person.init(person.x, person.y)
	}
	
	// Сбросить в исходное состояние
	private fun reset() {
		person.len = 0
		auth = ""
		block = true
		num = -1; gold = 0
		buffer = byteArrayOf(0, 0)
		pool.clear()
	}
	
	// Создать уровни классического пакета
	fun default(context: Context) {
		// запустить в отдельном потоке
		//thread {
			delete { where { system eq PACK_DEFAULT } }
			// проверить содержимое папки assets/classic
			val classic = context.assets.list(PACK_DEFAULT)
//			if(classic.size < 30) error("System of assets\\$PACK_DEFAULT not found!")
			pack = PACK_DEFAULT
			classic.forEach {
				var res = true
				// создать классические уровни
				try {
					num = it.substringBeforeLast('.').toLong()
					val map = context.assets.open("$PACK_DEFAULT/$it").reader().releaseRun { readLines() }
					auth = map[0]
					buffer = byteArrayOf2D(map[1].length, map.size - 1)
					
					repeat(height) {y ->
						repeat(width) {x ->
							var tile = charsOfLevelMap.search(map[y + 1][x], T_NULL.toInt())
							if((remapProp[tile] and MSKO) == O_FIRE) tile = T_FIRE0 + rnd.nextInt(4)
							buffer[x, y] = tile
						}
					}
				} catch(e: IOException) {
					reset()
					res = false
				}
				if(res) res = Level.store(context)
				if(!res) context.resources.getString(R.string.error_make_or_save, Level.num).debug()
			}
		//}
	}
}