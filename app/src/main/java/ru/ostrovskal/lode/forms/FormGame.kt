package ru.ostrovskal.lode.forms

import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ostrovskal.ssh.Constants
import com.github.ostrovskal.ssh.Constants.ACTION_EXIT
import com.github.ostrovskal.ssh.Constants.WRAP
import com.github.ostrovskal.ssh.Form
import com.github.ostrovskal.ssh.ui.*
import com.github.ostrovskal.ssh.utils.config
import com.github.ostrovskal.ssh.utils.debug
import com.github.ostrovskal.ssh.utils.dp
import com.github.ostrovskal.ssh.utils.send
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.R
import ru.ostrovskal.lode.gameView
import ru.ostrovskal.lode.msg
import ru.ostrovskal.lode.views.ViewGame

class FormGame : Form() {
	// поверхность
	lateinit var game: ViewGame
	
	override fun backPressed() {
		val tm = if(twicePressed()) tmBACK + 2000 else System.currentTimeMillis() + 10000
		if(game.status == STATUS_LOOP) {
			if(tm > System.currentTimeMillis()) {
				// Если тест - просто выход
				if(game.isTest) {
					sendResult(Constants.MSG_FORM, Constants.ACTION_EXIT)
				} else {
					wnd.instanceForm(FORM_DLG_G_ACTIONS)
				}
			} else {
				wnd.showToast(getString(R.string.again_press_for_exit), true, null, ToastLayout())
			}
		}
		tmBACK = System.currentTimeMillis()
	}
	
	override fun twicePressed() = true
	
	override fun handleMessage(msg: Message) {
		val s = game.surHandler
		if(s != null) {
			msg.apply {
				"onMessageFormGame(what: ${what.msg} arg1: ${arg1.msg} arg2: $arg2 obj: $obj)".debug()
				when(arg1) {
					ACTION_LOAD         -> s.send(a1 = ACTION_LOAD, a2 = arg2)
					ACTION_EXIT         -> footer(Constants.BTN_NO, 0)
					ACTION_FINISH       -> {}//game.finishForm()
				}
			}
		}
	}
	
	override fun inflateContent(container: LayoutInflater): UiCtx {
		val port = config.isVert
		return UI {
			linearLayout(port) {
				containerLayout(100, 95, true) {
					id = R.id.gameContainer
					game = gameView { id = R.id.game }
				}.lps(WRAP, WRAP)
				root = cellLayout(15, 1, 1.dp) {
					// очки
					button(style_tile_lode) { numResource = R.integer.TILE_SCORE }.lps(0, 0, 1, 1)
					text(R.string.empty_score, style_text_counters).lps(1, 0, 6, 1)
					// жизни, золото, уровень
					repeat(3) {
						button(style_tile_lode) { numResource = tilesGamePanel[it] }.lps(4 + it * 4, 0, 1, 1)
						text(R.string.empty_counter, style_text_counters).lps(5 + it * 4, 0, 3, 1)
					}
				}
			}
		}
	}
	
	override fun initContent(content: ViewGroup) {
		game.position = arguments.getInt("position")
		game.isTest = arguments.containsKey("isTest")
	}
	
	override fun saveState(state: Bundle) {
		game.saveState(state)
		super.saveState(state)
	}
	
	override fun restoreState(state: Bundle) {
		super.restoreState(state)
		game.restoreState(state)
	}
	
	override fun setAnimation(trans: FragmentTransaction) { trans.setTransition(FragmentTransaction.TRANSIT_NONE) }
	
	override fun onDestroy() {
		game.holder.removeCallback(game)
		super.onDestroy()
	}
}
