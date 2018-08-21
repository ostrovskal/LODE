package ru.ostrovskal.lode.forms

import android.os.Message
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.StylesAndAttrs
import com.github.ostrovskal.ssh.adapters.SelectAdapter
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.*
import com.github.ostrovskal.ssh.widgets.Edit
import com.github.ostrovskal.ssh.widgets.EditInvalidException
import com.github.ostrovskal.ssh.widgets.lists.Select
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Pack

class FormNP: Form() {
	private var isNew               = false
	private var nPack                = ""
	private var oldPack             = ""
	private var oldNum              = -1L
	
	override fun onItemClick(select: Select, view: View, position: Int, id: Long) {
		val sel = select.selectionString
		if(sel == "...") {
			wnd.instanceForm(FORM_DLG_NEW_PACK)
			//select.selectionString = nPack
		}
		nPack = sel
	}
	
	
	override fun inflateContent(container: LayoutInflater): UiCtx {
		isNew = index == FORM_LEVEL_NEW
		nPack = Level.pack
		if(nPack.isEmpty()) nPack = KEY_PACK.optText
		return UI {
			include(PlanetNP(index == FORM_LEVEL_NEW, nPack)) {}
		}
	}
	
	override fun initContent(content: ViewGroup) {
		if(!isNew) {
			with(Level) {
				oldNum = num
				oldPack = nPack
				content.byId<Edit>(R.id.etNumber).int = oldNum.toInt()
				content.byId<Edit>(R.id.etWidth).int = width
				content.byId<Edit>(R.id.etHeight).int = height
			}
		}
		content.byId<Select>(R.id.slPack).itemClickListener = this@FormNP
	}
	
	override fun handleMessage(msg: Message) {
		msg.apply {
			if(arg1 != ACTION_PACK) return@apply
			if(arg2 == Constants.BTN_OK) {
				val name = obj.toString()
				content.byId<Select>(R.id.slPack).apply {
					adapter = SelectAdapter(wnd, SelectPopup(), SelectItem(), Pack.listOf(Pack.name, Pack.name) + "...")
					selectionString = name
				}
				nPack = name
			}
			else content.byId<Select>(R.id.slPack).selectionString = nPack
		}
	}
	
	override fun footer(btnId: Int, param: Int) {
		if(btnId == Constants.BTN_OK) {
			try {
				// номер
				var nNum = content.byId<Edit>(R.id.etNumber).valid.toLong()
				// ширина
				val nWidth = if(!isNew) Level.width else content.byId<Edit>(R.id.etWidth).valid.toInt()
				// высота
				val nHeight = if(!isNew) Level.height else content.byId<Edit>(R.id.etHeight).valid.toInt()
				// изменилось имя или система?
				val isPack = nPack != oldPack && oldPack != ""
				// изменился номер?
				val isNum = nNum != oldNum && oldNum != -1L
				// если изменилась позиция уровня, удаляем уровень по старому номеру и сжимаем позиции, после его номера
				if(isNum || isPack) Level.delete(false)
				// если уровень  новый или изменился пакет или изменилась позиция - вставляем позицию уровня
				if(isNum || isPack || isNew) {
					if(Level.exist { Level.system.eq(nPack) and Level.position.eq(nNum) }) {
						Level.select(Level.position) {
							where { Level.system.eq(nPack) and Level.position.greaterEq(nNum) }
							orderBy(Level.position, false)
						}.execute()?.release {
							forEach {_ ->
								val n = integer(0)
								Level.update {
									it[Level.position] = n + 1
									where { Level.system.eq(nPack) and Level.position.eq(n) }
								}
							}
						}
					}
					else {
						// Если такой позиции нет - вставляем в конец
						nNum = Level.count { Level.system eq nPack }
					}
				}
				// Изменяем характеристики планеты, на новые:
				with(Level) {
					num = nNum
					pack = nPack
					auth = KEY_AUTHOR.optText
					buffer[0] = nWidth.toByte()
					buffer[1] = nHeight.toByte()
				}
			}
			catch(e: EditInvalidException) {
				val et = e.et ?: return
				et.startAnimation(shake)
				if(e.msg.isNotEmpty()) wnd.showToast(e.msg, parent = e.et)
				et.requestFocus()
				return
			}
			val sel = content.byId<Select>(R.id.slType).selection
			KEY_TYPE_LEVEL.optInt = sel
			sendResult(Constants.MSG_FORM, if(!isNew) ACTION_SAVE else ACTION_NEW, if(!isNew) 0 else sel)
		}
		super.footer(btnId, 0)
	}
	
	class PlanetNP(private val isNew: Boolean, private val sys: String) : UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			val port = config.isVert
			cellLayout(32, if(port) 17 else 15) {
				formHeader(if(isNew) R.string.header_level_new else R.string.header_level_prop)
				backgroundSet(StylesAndAttrs.style_form)
				val dy = if(port) 2 else 0
				select {
					id = R.id.slType
					isEnabled = isNew
					adapter = SelectAdapter(ctx, SelectPopup(), SelectItem(), ctx.arrayStr(R.array.levelTypes).toList())
					selection = KEY_TYPE_LEVEL.optInt
				}.lps(0, 0, if(port) 32 else 16, 2)
				select {
					id = R.id.slPack
					adapter = SelectAdapter(ctx, SelectPopup(), SelectItem(), Pack.listOf(Pack.name, Pack.name) + "...")
					selectionString = sys
				}.lps(if(port) 0 else 16, dy)
				editLayout {
					edit(R.id.etNumber, R.string.hint_number) {
						inputType = InputType.TYPE_CLASS_NUMBER
						range = 0..99
					}
				}.lps(8, 2 + dy, 16, 3)
				editLayout {
					edit(R.id.etWidth, R.string.hint_width) {
						inputType = InputType.TYPE_CLASS_NUMBER
						range = 14..40
						isEnabled = isNew
					}
				}.lps(8, 5 + dy, 16, 3)
				editLayout {
					edit(R.id.etHeight, R.string.hint_height) {
						inputType = InputType.TYPE_CLASS_NUMBER
						range = 14..40
						isEnabled = isNew
					}
				}.lps(8, 8 + dy, 16, 3)
				formFooter(Constants.BTN_OK, R.integer.I_YES, Constants.BTN_NO, R.integer.I_NO)
			}
		}
	}
}
