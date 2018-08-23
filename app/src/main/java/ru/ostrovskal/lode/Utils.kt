package ru.ostrovskal.lode

import android.view.ViewManager
import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.ui.uiView
import com.github.ostrovskal.ssh.utils.test
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.views.ViewEditor
import ru.ostrovskal.lode.views.ViewGame

inline fun ViewManager.editorView(init: ViewEditor.() -> Unit) = uiView({ ViewEditor(it) }, init)
inline fun ViewManager.gameView(init: ViewGame.() -> Unit) = uiView({ ViewGame(it) }, init)

val Int.obj get() = when(this) {
	O_PERSON -> "PERSON"
	O_ENEMY1 -> "ENEMY1"
	O_ENEMY2 -> "ENEMY2"
	O_POLZV  -> "POLZV"
	O_POLZH  -> "POLZH"
	O_PLATV  -> "PLATV"
	O_PLATH  -> "PLATH"
	O_FIRE   -> "FIRE"
	O_BETON  -> "BETON"
	O_WALL   -> "WALL"
	O_TRAP   -> "TRAP"
	O_TRAPN  -> "TRAPN"
	O_BOX    -> "BOX"
	O_BRIDGE -> "BRIDGE"
	O_GOLD   -> "GOLD"
	O_BUTTON -> "BUTTON"
	O_WALLN  -> "WALLN"
	O_NULL   -> "NULL"
	else     -> "UNDEF"
	
}

val Int.arr: String get() {
	val e = if(this test MSKE) "E " else ""
	val z = if(this test MSKZ) "Z " else ""
	val x = if(this test MSKX) "X " else ""
	val v = this and MSKT
	return "${v.obj} $e$z$x"
}

val Int.prop: String get() {
	val fa = if(this test FA) "FA " else ""
	val fp = if(this test FP) "FP " else ""
	val fz = if(this test FZ) "FZ " else ""
	val fe = if(this test FE) "FE " else ""
	val fb = if(this test FB) "FB " else ""
	val fx = if(this test FX) "FX " else ""
	val ff = if(this test FF) "FF " else ""
	val fd = if(this test FD) "FD " else ""
	val fn = if(this test FN) "FN " else ""
	val ft = if(this test FT) "FT " else ""
	val v = this and MSKO
	return "${v.obj} $fa$fp$fd$fn$ft$ff$fb$fe$fz$fx"
}

val Int.msg get() = when(this) {
	ACTION_FINISH   -> "ACTION_FINISH"
	ACTION_PACK     -> "ACTION_PACK"
	ACTION_LOAD     -> "ACTION_LOAD"
	ACTION_SAVE     -> "ACTION_SAVE"
	ACTION_DELETE   -> "ACTION_DELETE"
	ACTION_NEW      -> "ACTION_NEW"
	ACTION_PROP     -> "ACTION_PROP"
	ACTION_NAME     -> "ACTION_NAME"
	ACTION_GENERATE -> "ACTION_GENERATE"
	ACTION_EXIT     -> "ACTION_EXIT"
	STATUS_INIT     -> "STATUS_INIT"
	STATUS_DEAD     -> "STATUS_DEAD"
	STATUS_CLEARED  -> "STATUS_CLEARED"
	STATUS_PREPARED -> "STATUS_PREPARED"
	STATUS_LOOP     -> "STATUS_LOOP"
	STATUS_MESSAGE  -> "STATUS_MESSAGE"
	STATUS_EXIT     -> "STATUS_EXIT"
	STATUS_SUICIDED -> "STATUS_SUICIDED"
	STATUS_UNK      -> "STATUS_UNK"
	MSG_SERVICE     -> "MSG_SERVICE"
	MSG_FORM        -> "MSG_FORM"
	else            -> this.toString()
}
