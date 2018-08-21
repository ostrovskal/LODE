package ru.ostrovskal.lode.forms

import android.content.Context
import android.content.Loader
import android.database.Cursor
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.StylesAndAttrs
import com.github.ostrovskal.ssh.Theme
import com.github.ostrovskal.ssh.adapters.ListAdapter
import com.github.ostrovskal.ssh.adapters.SelectAdapter
import com.github.ostrovskal.ssh.sql.Rowset
import com.github.ostrovskal.ssh.sql.transaction
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.*
import com.github.ostrovskal.ssh.widgets.Text
import com.github.ostrovskal.ssh.widgets.Tile
import com.github.ostrovskal.ssh.widgets.lists.BaseListView
import com.github.ostrovskal.ssh.widgets.lists.ListView
import com.github.ostrovskal.ssh.widgets.lists.Select
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Pack

class FormOpen: Form() {
	
	override fun onItemClick(select: Select, view: View, position: Int, id: Long) {
		val nPack = select.selectionString
		if(nPack != result) {
			result = nPack
			connector?.apply { update(queryConnector()); forceLoad() }
		}
		adapter.path = "${Constants.folderFiles}/miniatures/$result"
	}
	
	override fun queryConnector() = Level.select(Level.position, Level.position, Level.create, Level.position, Level.id) {
		where { Level.system eq result }
		orderBy(Level.position)
	}
	
	override fun inflateContent(container: LayoutInflater): UiCtx {
		return UI {
			linearLayout {
				formHeader(R.string.header_level_open)
				backgroundSet(StylesAndAttrs.style_form)
				select {
					id = R.id.slPack
					val tmp = Level.pack
					result = if(tmp.isEmpty()) KEY_PACK.optText else tmp
					adapter = SelectAdapter(wnd, SelectPopup(), SelectItem(), Pack.listOf(Pack.name, Pack.name))
					itemClickListener = this@FormOpen
					selectionString = result
				}
				list {
					id = android.R.id.list
					adapter = PlanetAdapter(wnd).apply { this@FormOpen.adapter = this }
					itemClickListener = object : BaseListView.OnListItemClickListener {
						override fun onItemClick(list: BaseListView, view: View, position: Int, id: Long) {
							Level.pack = result
							sendResult(Constants.MSG_FORM, ACTION_LOAD, position)
							footer(Constants.BTN_NO, 0)
						}
					}
				}
			}
		}
	}
	
	override fun initContent(content: ViewGroup) {
		loaderManager.initLoader(Constants.LOADER_CONNECTOR, null, this).forceLoad()
	}
	
	override fun onLoadFinished(loader: Loader<Rowset>, data: Rowset?) {
		super.onLoadFinished(loader, data)
		val lst = content.byIdx<ListView>(2)
		if(lst.mSelection == -1) {
			wnd.findForm<FormEditor>("editor")?.apply {
				lst.selection = editor.position
			}
		}
	}
	
	private inner class PlanetAdapter(context: Context) : ListAdapter(context, OpenItem(), 3), View.OnClickListener {
		override fun bindView(view: View, context: Context, rs: Rowset) {
			val num = rs.integer(Level.position)
			val pos = rs.position
			view.byIdx<Tile>(3).apply {
				visibility = if(pos == 0) View.INVISIBLE else View.VISIBLE
				tag = num
				setOnClickListener(this@PlanetAdapter)
			}
			view.byIdx<Tile>(4).apply {
				visibility = if(pos >= rs.count - 1) View.INVISIBLE else View.VISIBLE
				tag = num
				setOnClickListener(this@PlanetAdapter)
			}
			super.bindView(view, context, rs)
		}
		
		override fun bindField(view: View?, rs: Rowset, idx: Int) {
			if(view is Text && idx == 1)
				view.text = (rs.integer(idx) + 1).toString().padStart(3, '0')
			else if(view is Text && idx == 2)
				view.text = rs.integer(idx).datetime
			else
				super.bindField(view, rs, idx)
		}
		
		override fun onClick(v: View) {
			val numFrom = (v.tag as? Long) ?: return
			Level.select(Level.position) {
				where { Level.system eq result}
				orderBy(Level.position)
			}.execute()?.release {
				// находим позицию курсорса в соответствии с исходной
				while(numFrom != integer(Level.position)) { moveToNext() }
				if(v.rotation >= 90f) moveToNext() else moveToPrevious()
				val numTo = integer(Level.position)
				// обменять номер у планет
				transaction {
					Level.update {
						it[Level.position] = 100//numTo
						where { (Level.system eq result) and (Level.position eq numFrom ) }
					}
					Level.update {
						it[Level.position] = numFrom
						where { (Level.system eq result) and (Level.position eq numTo ) }
					}
					Level.update {
						it[Level.position] = numTo
						where { (Level.system eq result) and (Level.position eq 100L) }
					}
				}
				// проверим, если текущая планета имеет тот же номер и пакет - обновить номер
				Level.apply {
					if(result == pack) {
						num = when(num) {
							numFrom	-> numTo
							numTo	-> numFrom
							else    -> num
						}
					}
				}
				loaderManager.getLoader<Cursor>(Constants.LOADER_CONNECTOR).forceLoad()
			}
		}
	}
	
	class OpenItem: UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			cellLayout(25, 10) {
				backgroundSet(StylesAndAttrs.style_item)
				padding = 1.dp
				layoutParams = LinearLayout.LayoutParams(Constants.MATCH, Theme.dimen(ctx, R.dimen.heightDlgItemOpenPlanet))
				button(StylesAndAttrs.style_icon) { numResource = R.integer.I_BLOCKED }.lps(1, 1, 8, 8)
				text(R.string.panel_text, style_text_level).lps(10, 3, 10, 3)
				text(R.string.null_text, StylesAndAttrs.style_text_small) {
					text = System.currentTimeMillis().datetime
					gravity = Gravity.CENTER
				}.lps(10, 6, 10, 3)
				button(StylesAndAttrs.style_tool_arrow).lps(20, 0, 5, 5)
				button(StylesAndAttrs.style_tool_arrow) { rotation = 180f }.lps(20, 5, 5, 5)
			}
		}
	}
}
