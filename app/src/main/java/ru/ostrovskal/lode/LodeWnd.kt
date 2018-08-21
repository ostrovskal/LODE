package ru.ostrovskal.lode

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Theme
import com.github.ostrovskal.ssh.Wnd
import com.github.ostrovskal.ssh.layouts.AbsLayout
import com.github.ostrovskal.ssh.singleton.Settings
import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.sql.SQL
import com.github.ostrovskal.ssh.ui.UiComponent
import com.github.ostrovskal.ssh.ui.UiCtx
import com.github.ostrovskal.ssh.ui.absoluteLayout
import com.github.ostrovskal.ssh.ui.setContent
import com.github.ostrovskal.ssh.utils.*
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Pack

class LodeWnd: Wnd() {
	lateinit var main: AbsLayout
	
	companion object {
		/**
		 * Установка уровня звука и музыки
		 */
		fun soundVolume()
		{
			val snd = if(KEY_SOUND.optBool) KEY_SOUND_VOLUME.optInt else 0
			val mus = if(KEY_MUSIC.optBool) KEY_MUSIC_VOLUME.optInt else 0
			Sound.setVolume(snd / 20f, mus / 20f)
		}
		
		/**
		 * Проверить на режим автора
		 */
		fun isAuthor() = KEY_AUTHOR_COUNT.optInt > 10 && KEY_AUTHOR.optBool
		
		/**
		 * Применить масштаб
		 */
		fun applyScale(value: Int): Int {
			val scl = if(KEY_SCALE.optBool) KEY_SCALE_VOLUME.optInt else 15
			return (value * (scl / 15f)).toInt()
		}
		
		/**
		 * Применить скорость
		 */
		fun applySpeed(value: Int): Long {
			val spd = if(KEY_SPEED.optBool) KEY_SPEED_VOLUME.optInt else 15
			return (value * (2f - (spd / 15f))).toLong()
		}
	}
	
	// Применение темы
	override fun applyTheme() {
		val theme = if(KEY_THEME.optInt == 0) themeDark else themeLight
		Theme.setTheme(this, theme)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		startLog(this, "LODE", true, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, BuildConfig.DEBUG)
		super.onCreate(savedInstanceState)
		Main().setContent(this, Constants.APP_GAME)
		// загружаем фрагмент
		if(savedInstanceState == null) instanceForm(FORM_MENU)
	}
	
	override fun initialize(restart: Boolean) {
		if(wndHandler == null) {
			// Создаем UI хэндлер
			wndHandler = Handler(Looper.getMainLooper(), this)
			// Инициализируем установки
			Settings.initialize(getSharedPreferences(Constants.logTag, Context.MODE_PRIVATE), arrayStr(R.array.settings))
			// Применяем тему и устанавливаем массивы
			applyTheme()
			// Запускаем звуки
			Sound.initialize(this, 5, arrayStr(R.array.sound), arrayIDs(R.array.music))
			// Запускаем БД
			SQL.connection(this, false, Pack, Level) {
				var res = it
				if(res) res = Pack.check()
				if(!res) {
					Pack.default()
					Level.default(this@LodeWnd)
				}
			}
		}
	}
	
	inner class Main : UiComponent() {
		override fun createView(ui: UiCtx) = with(ui) {
			main = absoluteLayout { id = R.id.mainContainer }
			main
		}
	}
}
