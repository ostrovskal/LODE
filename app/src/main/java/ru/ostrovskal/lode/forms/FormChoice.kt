package ru.ostrovskal.lode.forms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.StylesAndAttrs
import com.github.ostrovskal.ssh.Theme
import com.github.ostrovskal.ssh.adapters.ListAdapter
import com.github.ostrovskal.ssh.adapters.SelectAdapter
import com.github.ostrovskal.ssh.sql.Rowset
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.optText
import com.github.ostrovskal.ssh.widgets.Tile
import com.github.ostrovskal.ssh.widgets.lists.BaseListView
import com.github.ostrovskal.ssh.widgets.lists.Select
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.tables.Level
import ru.ostrovskal.lode.tables.Pack

class FormChoice: Form() {
	override fun onItemClick(select: Select, view: View, position: Int, id: Long) {
		val pack = select.selectionString
		if(pack != result) {
			result = pack
			connector?.apply { update(queryConnector()); forceLoad() }
		}
		adapter.path = "${Constants.folderFiles}/miniatures/$result"
	}
	
	override fun queryConnector() = Level.select(Level.title, Level.title, Level.blocked, Level.id) {
		where { Level.system.eq(result) }
		orderBy(Level.position)
	}
	
	override fun initContent(content: ViewGroup) {
		loaderManager.initLoader(LOADER_CONNECTOR, null, this).forceLoad()
	}
	
	override fun inflateContent(container: LayoutInflater): UiCtx {
		return UI {
			result = KEY_PACK.optText
			linearLayout {
				backgroundSet(StylesAndAttrs.style_form)
				formHeader(R.string.header_choice)
				select {
					id = R.id.slPack
					adapter = SelectAdapter(wnd, SelectPopup(), SelectItem(), Pack.listOf(Pack.name, Pack.name))
					selectionString = result
					itemClickListener = this@FormChoice
				}
				grid {
					id = android.R.id.list
					cellSize = Theme.dimen(ctx, R.dimen.heightDlgItemChoicePlanet)
					adapter = PlanetAdapter(wnd).apply {
						this@FormChoice.adapter = this
						path = "${Constants.folderFiles}/miniatures/$result"
					}
					itemClickListener = object : BaseListView.OnListItemClickListener {
						override fun onItemClick(list: BaseListView, view: View, position: Int, id: Long) {
							if(Level.exist { Level.system.eq(result) and Level.blocked.eq(0) and Level.id.eq(id) }) {
								wnd.apply {
									Level.pack = result
									footer(BTN_NO, 0)
									instanceForm(FORM_GAME, "position", position)
								}
							}
						}
					}
				}
			}
		}
	}
	
	private class ChoiceItem: UiComponent() {
		override fun createView(ui: UiCtx) = ui.run {
			cellLayout(10, 12) {
				layoutParams = LinearLayout.LayoutParams(MATCH, Theme.dimen(ctx, R.dimen.heightDlgItemChoicePlanet))
				backgroundSet(StylesAndAttrs.style_item)
				button(StylesAndAttrs.style_icon) { scale = Constants.TILE_SCALE_MIN }.lps(1, 1, 8, 8)
				text(R.string.panel_text, style_text_level).lps(0, 9, 10, 3)
			}
		}
	}
	
	private class PlanetAdapter(context: Context) : ListAdapter(context, ChoiceItem(), 2) {
		private val iCancel = context.resources.getInteger(R.integer.I_BLOCKED)
		
		override fun bindField(view: View?, rs: Rowset, idx: Int) {
			if(view is Tile && rs.boolean("blocked"))
				view.setBitmap("icon_tiles", 10, 3, iCancel)
			else super.bindField(view, rs, idx)
		}
	}
}
