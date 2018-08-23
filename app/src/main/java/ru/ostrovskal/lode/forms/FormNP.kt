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
	private var oldName             = ""
	private var oldPack             = ""
	private var oldNum              = -1L
	private var oldWidth            = -1
	private var oldHeight           = -1
	
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
				oldName = name
				oldPack = nPack
				oldWidth = width
				oldHeight = height
				content.byId<Edit>(R.id.etNumber).int = oldNum.toInt()
				content.byId<Edit>(R.id.etName).string = oldName
				content.byId<Edit>(R.id.etWidth).int = oldWidth
				content.byId<Edit>(R.id.etHeight).int = oldHeight
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
				// имя
				val etName = content.byId<Edit>(R.id.etName)
				val nName = etName.valid
				// ширина
				val nWidth = content.byId<Edit>(R.id.etWidth).valid.toInt()
				// высота
				val nHeight = content.byId<Edit>(R.id.etHeight).valid.toInt()
				// изменилось имя или система?
				val isPack = nPack != oldPack && oldPack != ""
				val isName = nName != oldName && oldName != ""
				// изменился номер?
				val isNum = nNum != oldNum && oldNum != -1L
				// Проверить на существование уже такой планеты с именем, если оно изменилось
				if(isName) {
					if(Level.exist { Level.system.eq(nPack) and Level.title.eq(nName) } )
						throw EditInvalidException(getString(R.string.level_name_already_exist, nName), etName)
				}
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
					name = nName
					pack = nPack
					auth = KEY_AUTHOR.optText
					if(!isNew && nWidth != oldWidth || nHeight != oldHeight) Level.resize(nWidth, nHeight)
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
			sendResult(Constants.MSG_FORM, if(!isNew) ACTION_PROP else ACTION_NEW, if(!isNew) 0 else sel)
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
				val h = if(port) 3 else 4
				select {
					id = R.id.slType
					isEnabled = isNew
					adapter = SelectAdapter(ctx, SelectPopup(), SelectItem(), ctx.arrayStr(R.array.levelTypes).toList())
					selection = KEY_TYPE_LEVEL.optInt
				}.lps(0, 0, if(port) 32 else 16, h - 1)
				select {
					id = R.id.slPack
					adapter = SelectAdapter(ctx, SelectPopup(), SelectItem(), Pack.listOf(Pack.name, Pack.name) + "...")
					selectionString = sys
				}.lps(if(port) 0 else 16, dy)
				editLayout {
					edit(R.id.etNumber, R.string.hint_number) {
						inputType = InputType.TYPE_CLASS_NUMBER
						range = 0..49
					}
				}.lps(0, 3 + dy, 16, h)
				editLayout {
					edit(R.id.etName, R.string.hint_name)
				}.lps(16, 3 + dy, 16, h)
				editLayout {
					edit(R.id.etWidth, R.string.hint_width) {
						inputType = InputType.TYPE_CLASS_NUMBER
						range = 10..50
					}
				}.lps(0, 7 + dy, 16, h)
				editLayout {
					edit(R.id.etHeight, R.string.hint_height) {
						inputType = InputType.TYPE_CLASS_NUMBER
						range = 10..50
					}
				}.lps(16, 7 + dy, 16, h)
				formFooter(Constants.BTN_OK, R.integer.I_YES, Constants.BTN_NO, R.integer.I_NO)
			}
		}
	}
}
