package ru.ostrovskal.lode.forms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.StylesAndAttrs
import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.ui.UI
import com.github.ostrovskal.ssh.ui.backgroundSet
import com.github.ostrovskal.ssh.ui.button
import com.github.ostrovskal.ssh.ui.cellLayout
import com.github.ostrovskal.ssh.utils.config
import com.github.ostrovskal.ssh.utils.optInt
import com.github.ostrovskal.ssh.utils.optText
import com.github.ostrovskal.ssh.utils.sql
import com.github.ostrovskal.ssh.widgets.Tile
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.LodeWnd
import ru.ostrovskal.lode.R.integer.*
import ru.ostrovskal.lode.tables.Level

class FormMenu: Form() {
	private val coordPort   = intArrayOf(6, 3, 0, 10, 12, 10, 0, 24, 0, 24, 12, 24)
	private val coordLand   = intArrayOf(12, 1, 3, 7, 21, 7, 0, 14, 0, 14, 24, 14)
	private val btnIcons    = intArrayOf(I_GAME, I_CHOICE_LEVEL, I_OPTIONS, I_EDITOR, I_HELP, I_EXIT)
	private val buttons     = mutableListOf<Tile>()
	
	override fun inflateContent(container: LayoutInflater) = UI {
		buttons.clear()
		val vert = config.isVert
		val coord = if(vert) coordPort else coordLand
		cellLayout(if(vert) 16 else 28, if(vert) 28 else 18) {
			backgroundSet(StylesAndAttrs.style_menu)
			btnIcons.forEachIndexed { index, icon ->
				buttons.add(button(StylesAndAttrs.style_tool) {
					setOnClickListener(this@FormMenu)
					iconResource = icon
				}.lps(coord[index * 2], coord[index * 2 + 1], 4, 4))
			}
		}
	}
	
	override fun initContent(content: ViewGroup) {
		// при первом запуске - запускать анимацию справки
		if(arguments.getBoolean("first")) {
			arguments.putBoolean("first", false)
			shake?.repeatCount = 5
			buttons[4].startAnimation(shake)
		}
		if(!LodeWnd.isAuthor()) {
			// кнопка справки
			buttons[4].visibility = View.GONE
		} else {
			// кнопка редактора
			buttons[3].visibility = View.GONE
		}
		// кнопка выбор планеты
		buttons[1].isEnabled = Level.exist { Level.blocked eq 0 }
		// устанавливаем громкость
		LodeWnd.soundVolume()
		// запускаем музыку в заставке
		Sound.playMusic(wnd, 0, true)
	}
	
	override fun onClick(v: View) {
		// Запомним текущую тему
		KEY_TMP_THEME.optInt = KEY_THEME.optInt
		// Запомним текущий пакет
		val pack = KEY_PACK.optText
		Level.pack = pack
		
		val idx = buttons.indexOf(v)
		var position = 0
		if(idx == FORM_GAME) {
			sql {
				Level.select(Level.position) {
					where { (Level.system eq pack) and (Level.blocked eq 1) }
					orderBy(Level.position)
					limit(1)
				}.execute()?.apply { position = this[Level.position].toInt() }
				if(position >= Level.count { Level.system eq pack }) position = 0
			}
		}
		wnd.instanceForm(idx, "position", position)
	}
}
