package ru.ostrovskal.lode.forms

import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.singleton.Sound
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.*
import com.github.ostrovskal.ssh.widgets.Text
import com.github.ostrovskal.ssh.widgets.Tile
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.editorView
import ru.ostrovskal.lode.msg
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.views.ViewEditor

class FormEditor : Form() {
	
	private var numLevel: Text?     = null
	
	// текущий тайл
	@JvmField var curTile: Tile? 	= null
	
	// поверхность
	lateinit var editor: ViewEditor
	
	override fun backPressed() {
		if(wnd.fragmentManager.backStackEntryCount != 0) footer(Constants.BTN_NO, 0)
	}
	
	override fun onResume() {
		editor.preview = KEY_EDIT_PREVIEW.optBool
		super.onResume()
	}
	
	override fun handleMessage(msg: Message) {
		val s = editor.surHandler
		if(s != null) {
			msg.apply {
				"onMessageFormEditor(what: ${what.msg} arg1: ${arg1.msg} arg2: $arg2 obj: $obj)".debug()
				when(arg1) {
					ACTION_LOAD           -> s.send(a1 = ACTION_LOAD, a2 = arg2)
					ACTION_GENERATE       -> s.send(a1 = ACTION_GENERATE)
					ACTION_DELETE         -> s.send(a1 = ACTION_DELETE)
					ACTION_NEW            -> s.send(a1 = ACTION_NEW, a2 = arg2)
					ACTION_SAVE           -> s.send(a1 = ACTION_SAVE, a2 = arg2)
					ACTION_NUM            -> {
						numLevel?.text = (Level.num + 1).toString().padStart(3, '0')
						KEY_EDIT_LEVEL.optText = "${Level.num}#${Level.pack}"
					}
					Constants.ACTION_EXIT -> footer(Constants.BTN_NO, 0)
				}
			}
		}
	}
	
	override fun inflateContent(container: LayoutInflater): UiCtx = UI {
		val port = config.isVert
		linearLayout {
			containerLayout(100, if(port) 84 else 80, true) {
				id = R.id.editorContainer
				editor = editorView { id = R.id.editor }
			}.lps(Constants.WRAP, Constants.WRAP)
			root = cellLayout(if(port) 38 else 87, 12, 1.dp) {
				backgroundSet(style_panel)
				val dx = if(port) 4 else 5
				var y = if(port) 4 else 6
				if(port) {
					repeat(2) {row ->
						repeat(9) {
							button(style_tile_lode) {
								setOnClickListener(this@FormEditor)
								numResource = tilesEditorPanel[row * 9 + it]
							}.lps(1 + dx * it, y, dx, 4)
						}
						y += 4
					}
				} else {
					repeat(17) {
						button(style_tile_lode) {
							setOnClickListener(this@FormEditor)
							numResource = tilesEditorPanel[it]
						}.lps(1 + dx * it, 6, dx, 6)
					}
				}
				numLevel = text(R.string.null_text, style_text_level) {
					setOnClickListener {
						if(fragmentManager.backStackEntryCount == 1) wnd.instanceForm(FORM_DLG_E_ACTIONS)
					}
				}.lps(0, 0, if(port) 38 else 87, 5)
			}
		}
	}
	
	override fun initContent(content: ViewGroup) {
		setTile(KEY_EDIT_TILE.optInt)
		val lst = KEY_EDIT_LEVEL.optText.split('#')
		if(lst.size == 2) {
			editor.position = lst[0].ival(0, 10)
			Level.pack = lst[1]
		}
		else {
			editor.position = 0
			Level.pack = PACK_DEFAULT
		}
		Sound.playMusic(wnd, 0, true)
	}
	
	override fun onClick(v: View) {
		setTile(root?.indexOfChild(v) ?: 0)
	}
	
	override fun footer(btnId: Int, param: Int) {
		// вызывать только, если других фрагментов нет
		if(editor.modify && fragmentManager.backStackEntryCount == 1) {
			editor.modify = false
			wnd.instanceForm(FORM_DLG_SAVE)
		}
		else super.footer(btnId, param)
	}
	
	override fun saveState(state: Bundle) {
		editor.saveState(state)
		super.saveState(state)
		numLevel?.apply { state.put("num_level", text) }
	}
	
	override fun restoreState(state: Bundle) {
		super.restoreState(state)
		editor.restoreState(state)
		numLevel?.apply { text = state.getString("num_level") }
	}
	
	override fun setAnimation(trans: FragmentTransaction) { trans.setTransition(FragmentTransaction.TRANSIT_NONE) }
	
	override fun onDestroy() {
		editor.holder.removeCallback(editor)
		super.onDestroy()
	}
	
	fun setTile(idx: Int) {
		curTile?.isChecked = false
		curTile = root?.byIdx(idx)
		curTile?.isChecked = true
		KEY_EDIT_TILE.optInt = idx
	}
}
