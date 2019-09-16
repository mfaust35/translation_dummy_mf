package com.faust.m.td

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.google.android.material.textfield.TextInputEditText

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun clearSoftInputFocus(focusedView: View) {
    focusedView.clearFocus()
    val iMM =
        focusedView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    iMM.hideSoftInputFromWindow(focusedView.windowToken, 0)
}

fun TextInputEditText.setOnEditorActionListener(listener:
                                    (textView: TextView, editorAction: EditorAction) -> Boolean) {
    setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent ->
        listener.invoke(textView, EditorAction(actionId, keyEvent))
    }
}

class EditorAction(private val actionId: Int, private val keyEvent: KeyEvent) {

    fun isActionDone() = EditorInfo.IME_ACTION_DONE == actionId

    fun isEnterKeyDownEvent() =
        KeyEvent.KEYCODE_ENTER == keyEvent.keyCode && KeyEvent.ACTION_DOWN == keyEvent.action

    fun isDone(): Boolean = isActionDone() || isEnterKeyDownEvent()
}