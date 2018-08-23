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
import kotlin.experimental.and
import kotlin.experimental.or

object Level: Table() {
	@JvmField val id        = integer("_id").notNull().primaryKey()
	@JvmField val system    = text("system").notNull().references(Pack.name, RuleOption.CASCADE, RuleOption.CASCADE)
	@JvmField val creator   = text("creator").notNull().default("OSTROV")
	@JvmField val title     = text("title").notNull()
	@JvmField val position  = integer("position").notNull().checked { it.between(0, 101) }
	@JvmField val blocked   = integer("blocked").notNull().default(1)
	@JvmField val content   = blob("content").notNull()
	@JvmField val create    = integer("create").notNull()
	
	@SqlField("position")   @JvmField var num     = 0L
	@SqlField("create")     @JvmField var date    = 0L
	@SqlField("blocked")    @JvmField var block   = true
	@SqlField("content")    @JvmField var buffer  = byteArrayOf(0, 0)
	@SqlField("title")      @JvmField var name    = ""
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
	
	inline val width                get()   = buffer[0].toInt()
	inline val height               get()   = buffer[1].toInt()
	
	// Поиск объекта по определенной позиции
	fun findBox(x: Int, y: Int): Object? {
		pool.forEach {
			if(it !is Box) return@forEach
			val xx = Math.abs(x - it.x)
			val yy = Math.abs(y - it.y)
			if(xx < SEGMENTS && yy < SEGMENTS) return it
		}
		return null
	}
	// Проверка на свойства элементов по определенным координатам на карте
	fun isProp(x: Int, y: Int, p: Int, l: Int = 1, ops: Int = OPS_FULL): Boolean {
		repeat(l) {
			if(remapProp[fromMap(x + it * SEGMENTS, y)] test p) return true
		}
		return false
	}

	// Установка/Очистка элементов в карте
	fun toMap(x: Int, y: Int, t: Byte, l: Int = 1, ops: Int = OPS_SET) {
		if(y < mapSegments.h && y >= 0 && x >= 0) {
			var xx = x
			val yy = y / SEGMENTS
			repeat(l) {
				if(xx < mapSegments.w) {
					val tt = fromMap(xx, y).toByte()
					buffer[xx / SEGMENTS, yy] = when(ops) {
						OPS_SET     -> t
						OPS_OR      -> tt or t
						OPS_AND     -> tt and t
						else        -> tt
					}
				}
				xx += SEGMENTS
			}
		}
	}
	
	// Получение значения элемента карты
	fun fromMap(x: Int, y: Int): Int {
		return if(y >= mapSegments.h || x >= mapSegments.w || y < 0 || x < 0) T_BETON.toInt() else buffer[x / SEGMENTS, y / SEGMENTS]
	}

	fun isIntersectPerson(x: Int, y: Int, inner: Boolean): Boolean {
		val xx = Math.abs(x - person.x)
		val yy = Math.abs(y - person.y)
		val sz = if(inner) SEGMENTS / 2 else SEGMENTS
		return xx <= sz && yy <= sz
	}
	
	// Генерировать уровень
	fun generator(context: Context, type: Int) {
		buffer = byteArrayOf2D(width, height)
		buffer.fill(tileGenLevel[type], 2, buffer.size)
		buffer[1, 1] = O_PERSON
		block = true
		store(context)
	}

	// Ресайз уровня
	fun resize(w: Int, h: Int) {
		val nbuffer = byteArrayOf2D(w, h)
		nbuffer.fill(T_NULL, 2, nbuffer.size)
		val hh = Math.min(h, height)
		val sz = Math.min(w, width)
		repeat(hh) { System.arraycopy(buffer, 2 + it * width, nbuffer, 2 + it * w, sz) }
		buffer = nbuffer
	}
	
	// Создать миниатюру
	private fun miniature(context: Context) {
		val dst = Rect()
		val src = Rect()
		val mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
		// определяем максимальный габарит
		val maxWidth = if(width > height) width else height
		val sz = BLOCK_MINIATURE * maxWidth
		// выравнивание по центру
		val offsX = (sz - BLOCK_MINIATURE * width) / 2
		val offsY = (sz - BLOCK_MINIATURE * height) / 2
		// создаем битмапы
		val tiles = context.createBitmap(R.drawable.sprites) ?: return
		val min = Bitmap.createBitmap(sz, sz, Bitmap.Config.ARGB_8888) ?: return
		val canvas = Canvas(min)
		val wh = tiles.width / 10
		// стираем превью
		canvas.drawColor(0)
		// нарисовать в битмап всю планету
		repeat(height) {y ->
			dst.top = offsY + y * BLOCK_MINIATURE
			dst.bottom = dst.top + BLOCK_MINIATURE
			repeat(width) {x ->
				var v = remapTiles[buffer[x, y]]
				if(v == T_FIRE0.toInt()) {
					v = if(remapProp[buffer[x, y - 1]] and MSKT == O_FIRE) {
						lavaTiles[x and 3].toInt()
					} else {
						fireTiles[x and 7].toInt()
					}
				}
				src.left = v % 10 * wh
				src.top = v / 10 * wh
				src.right = src.left + wh
				src.bottom = src.top + wh
				dst.left = offsX + x * BLOCK_MINIATURE
				dst.right = dst.left + BLOCK_MINIATURE
				canvas.drawBitmap(tiles, src, dst, mPaint)
			}
		}
		tiles.recycle()

		FileOutputStream(makeDirectories("miniatures/$pack", Constants.FOLDER_FILES, "$name.png")).release {
			min.compress(Bitmap.CompressFormat.PNG, 100, this)
		}
		min.recycle()
	}
	
	// Сохранить уровень
	private fun save() {
		val sb = StringBuilder((width + 1) * height + auth.length + 1 + 10)
		// запомнить номер, автора
		sb.append("$num\n").append("$auth\n")
		// перекодировка
		repeat(height) { y ->
			repeat(width) { sb.append(charsOfLevelMap[remapEditorProp[buffer[it, y] and MSKO] and MSKO]) }
			sb.append('\n')
		}
		// запись
		makeDirectories("levels/$pack", Constants.FOLDER_FILES, "$name.lv").writeText(sb.toString())
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
		if(delete { where { system.eq(pack) and position.eq(num) and title.eq(name) } } > 0L) {
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
				makeDirectories("levels/$pack", Constants.FOLDER_FILES, "$name.lv").delete()
				// удалить миниатюру
				makeDirectories("miniatures/$pack", Constants.FOLDER_FILES, "$name.png").delete()
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
					O_POLZH  -> if(len > 1) pool.add(Polz(xx, yy, len, false)) else "Горизонтальный ползунок {$xx, $yy} имеет недопустимую длину {$len}".debug()
					O_POLZV  -> pool.add(Polz(xx, yy, len, true))
					O_BUTTON -> pool.add(Button(xx, yy))
					O_FIRE   -> pool.add(Fire(xx, yy, len))
					O_BOX    -> pool.add(Box(xx, yy))
					O_BRIDGE -> pool.add(Bridge(xx, yy, len))
					O_PLATH  -> pool.add(Platform(xx, yy, len, false))
					O_PLATV  -> pool.add(Platform(xx, yy, len, true))
					O_GOLD   -> gold += len
				}
				//"${o.obj} $len ${xx / SEGMENTS} ${yy / SEGMENTS}".info()
				if(o < O_BRIDGE || o >= O_BRIDGE && !isGame) toMap(xx, yy, o.toByte(), len)
			}
		}
		pool.clear()
		person.len = 0
		person.x = -1
		gold = 0
		mapSegments.set(width * SEGMENTS, height * SEGMENTS)
		
//		var tbuffer = byteArrayOf2D(width * SEGMENTS, height * SEGMENTS)
		var tbuffer = byteArrayOf2D(width, height)
		tbuffer.fill(O_NULL.toByte(), 2, tbuffer.size)
		val nbuffer = buffer
		buffer = tbuffer
		tbuffer = nbuffer
		
		repeat(height) {y ->
			var o = remapEditorProp[tbuffer[0, y]] and MSKO
			var len = 0
			repeat(width) { x ->
				val cur = remapEditorProp[tbuffer[x, y]] and MSKO
				if(cur != o || cur >= O_BUTTON) {
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
		auth = ""; name = ""
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
					name = it.substringBeforeLast('.')
					val map = context.assets.open("$PACK_DEFAULT/$it").reader().releaseRun { readLines() }
					num = map[0].toLong()
					auth = map[1]
					buffer = byteArrayOf2D(map[2].length, map.size - 2)
					
					repeat(height) {y ->
						repeat(width) { x -> buffer[x, y] = charsOfLevelMap.search(map[y + 2][x], T_NULL.toInt()) }
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