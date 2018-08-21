package ru.ostrovskal.lode.forms

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.StylesAndAttrs
import com.github.ostrovskal.ssh.adapters.SelectAdapter
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.*
import com.github.ostrovskal.ssh.widgets.Html
import com.github.ostrovskal.ssh.widgets.Text
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.tables.Pack
import kotlin.properties.Delegates

class FormHelp: Form() {
	private var html            by Delegates.notNull<Html>()
	private var path: String?   = null
	private var scroll          = 0
	
	override fun backPressed()  {
		if(!html.back()) {
			wnd.apply {
				super.backPressed()
				if(index == FORM_FINISH) sendResult(Constants.MSG_FORM, Constants.ACTION_EXIT)
			}
		}
	}
	
	override fun onResume() {
		super.onResume()
		if(!html.valid) html.setArticle(path, scroll)
	}
	
	override fun saveState(state: Bundle) {
		super.saveState(state)
		html.save(state)
	}
	
	override fun restoreState(state: Bundle) {
		super.restoreState(state)
		html.restore(state)
		path = state.getString("path")
		scroll = state.getInt("scroll")
	}
	
	override fun inflateContent(container: LayoutInflater): UiCtx {
		return UI {
			linearLayout {
				formHeader(R.string.header_help)
				backgroundSet(StylesAndAttrs.style_form)
				cellLayout(10, 10) {
					html = html(if(index == FORM_FINISH) style_text_finish else StylesAndAttrs.style_text_html) {
						id = R.id.html
						langResource = R.string.lang
						root = when(index) {
							FORM_GAME_HELP   -> "html/game/"
							FORM_EDITOR_HELP -> "html/editor/"
							FORM_FINISH      -> {
								// проверить какую систему прошел и подставить соответствующий корень
								if(KEY_PACK.optText != PACK_DEFAULT) "html/finish2/"
								else {
									KEY_AUTHOR_COUNT.optInt = 20
									"html/finish1/"
								}
							}
							else             -> error("Unknown help source!")
						}
						if(index != FORM_FINISH) {
							var key = "theme_tool"
							aliases["tool"] = Html.Alias(key, wnd.bitmapGetCache(key), 3, 1, 0, 0, 30)
							key = "sprites"
							aliases["main"] = Html.Alias(key, wnd.bitmapGetCache(key), 10, 4, 0, 0, 23)
							key = "icon_tiles"
							aliases["icons"] = Html.Alias(key, wnd.bitmapGetCache(key), 10, 3, 0, 0, 30)
							key = "controller_tiles"
							aliases["cursor"] = Html.Alias(key, wnd.bitmapGetCache(key), 6, 1, 0, 0, 45)
							
							val width = 330.dp
							
							key = "game_panel$theme"
							aliases["game_panel"] = Html.Alias(key, GamePanel().makeBitmap(wnd, key, width, 165.dp))
							//key = "game_recv$theme"
							//aliases["game_recv"] = Html.Alias(key, FormRecv.RecvItem().makeBitmap(wnd, key, width, 165.dp))
							key = "game_actions$theme"
							aliases["game_actions"] = Html.Alias(key, GameActions().makeBitmap(wnd, key, 220.dp, 290.dp), 1, 1, 0, 0, 220, 290)
							if(index == FORM_EDITOR_HELP) {
								key = "help_editor_panel$theme"
								aliases["editor_panel"] = Html.Alias(key, EditorPanel().makeBitmap(wnd, key, width, 165.dp))
								key = "help_editor_new$theme"
								aliases["editor_new"] = Html.Alias(key, FormNP.PlanetNP(true, PACK_DEFAULT).makeBitmap(wnd, key, width, 270.dp))
								key = "help_editor_prop$theme"
								aliases["editor_prop"] = Html.Alias(key, FormNP.PlanetNP(false, PACK_DEFAULT).makeBitmap(wnd, key, width, 270.dp))
								key = "help_editor_open$theme"
								aliases["editor_open"] = Html.Alias(key, PlanetOpen().makeBitmap(wnd, key, width, 270.dp))
								key = "help_editor_actions$theme"
								aliases["editor_actions"] = Html.Alias(key, EditorActions().makeBitmap(wnd, key, 230.dp, 310.dp), 1, 1, 0, 0, 230, 310)
								key = "help_editor_send$theme"
								aliases["editor_send"] = Html.Alias(key, EditorSend().makeBitmap(wnd, key, width, 300.dp))
							}
						}
					}
				}
			}
		}
	}
	
	private class PlanetOpen: UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			linearLayout {
				formHeader(R.string.header_level_open)
				backgroundSet(StylesAndAttrs.style_form)
				select { adapter = SelectAdapter(ctx, SelectPopup(), SelectItem(), Pack.listOf(Pack.name, Pack.name)) }
				include(FormOpen.OpenItem()) {}
				include(FormOpen.OpenItem()) {
					it.byIdx<Text>(1).setText(R.string.panel_text2)
				}
			}
		}
	}
	
	private class EditorActions : UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			cellLayout(14, 20) {
				backgroundSet(StylesAndAttrs.style_form)
				formHeader(R.string.header_dialog_actions)
				var idx = 0
				var dx = 0
				repeat(4) { row ->
					repeat(3) {
						if(idx < iconsEditorActions.size) {
							if(idx == iconsEditorActions.size - 1) dx = 4
							button(style_button_actions) {
								iconResource = iconsEditorActions[idx++]
							}.lps(it * 4 + 1 + dx, row * 4, 4, 4)
						}
					}
				}
				formFooter(Constants.BTN_NO, R.integer.I_NO)
			}
		}
	}
	
	private class EditorPanel : UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			cellLayout(28, 18) {
				backgroundSet(style_panel)
				repeat(2) {row ->
					repeat(9) { button(style_panel_tile) { numResource = tilesEditorPanel[row * 9 + it] }.lps(2 + it * 4, 5 + row * 4, 4, 4) }
				}
				text(R.string.panel_text, style_text_level).lps(1, 1, 26, 3)
			}
		}
	}
	
	private class GamePanel: UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			cellLayout(15, 3, 1.dp, false) {
				backgroundSet(style_panel)
				val fpos = 0
				// очки
				button(style_panel_tile) { numResource = R.integer.TILE_SCORE }.lps(1 + fpos, 1, 1, 2)
				text(R.string.empty_score, style_text_counters).lps(2 + fpos, 1, 6, 2)
				// жизни, золото, уровень
				repeat(3) {
					button(style_panel_tile) { numResource = tilesGamePanel[it] }.lps(6 + fpos + it * 3, 1, 1, 2)
					text(R.string.empty_counter, style_text_counters).lps(7 + fpos + it * 3, 1, 3, 2)
				}
			}
		}
	}
	
	private class EditorSend: UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			cellLayout(10, 19, 1.dp) {
				formHeader(R.string.header_send)
				backgroundSet(StylesAndAttrs.style_form)
				repeat(10) {
					button(StylesAndAttrs.style_icon) { numResource = if(it > 4) R.integer.I_SKULL1 else R.integer.I_SKULL2 }.lps(it, 13, 1, 2)
				}
				//text(R.string.null_text, style_text_level) { text = context.getString(R.string.classic_30); gravity = Gravity.CENTER }.lps(0, 0, 10, 2)
				text(R.string.panel_text, StylesAndAttrs.style_text_small) { text = System.currentTimeMillis().datetime; gravity = Gravity.CENTER }.lps(0, 2, 10, 1)
				editLayout { edit(-1, R.string.hint_price) { range = 0..500 } }.lps(0, 3, 10, 3)
				editLayout { edit(-1, R.string.hint_desc) }.lps(0, 6, 10, 7)
				formFooter(Constants.BTN_OK, R.integer.I_YES, Constants.BTN_NO, R.integer.I_NO)
			}
		}
	}
	
	private class GameActions: UiComponent() {
		override fun createView(ui: UiCtx): View = with(ui) {
			cellLayout(14, 11, 1.dp) {
				backgroundSet(StylesAndAttrs.style_form)
				formHeader(R.string.header_dialog_actions)
				button(style_button_actions) {
					text = ctx.resources.getString(R.string.game_actions_exit)
				}.lps(0, 0, 14, 3)
				button(style_button_actions) {
					text = ctx.resources.getString(R.string.game_actions_restart)
				}.lps(0, 3, 14, 3)
				button(style_button_actions) {
					text = ctx.resources.getString(R.string.game_actions_continue)
				}.lps(0, 6, 14, 3)
			}
		}
	}
}