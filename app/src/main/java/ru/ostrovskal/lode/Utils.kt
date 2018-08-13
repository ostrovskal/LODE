package ru.ostrovskal.lode

import android.view.ViewManager
import com.github.ostrovskal.ssh.Constants.*
import com.github.ostrovskal.ssh.ui.uiView
import ru.ostrovskal.lode.Constants.*
import ru.ostrovskal.lode.views.ViewEditor
import ru.ostrovskal.lode.views.ViewGame

inline fun ViewManager.editorView(init: ViewEditor.() -> Unit) = uiView({ ViewEditor(it) }, init)
inline fun ViewManager.gameView(init: ViewGame.() -> Unit) = uiView({ ViewGame(it) }, init)

val Int.msg get() = when(this) {
	ACTION_FINISH   -> "ACTION_FINISH"
	ACTION_SYSTEM   -> "ACTION_SYSTEM"
	ACTION_LOAD     -> "ACTION_LOAD"
	ACTION_SAVE     -> "ACTION_SAVE"
	ACTION_DELETE   -> "ACTION_DELETE"
	ACTION_NEW      -> "ACTION_NEW"
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
