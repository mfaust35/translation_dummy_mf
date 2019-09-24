package com.faust.m.td.presentation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
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
                                                    (textView: TextView,
                                                     editorAction: EditorAction) -> Boolean) {
    setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent ->
        listener.invoke(textView, EditorAction(actionId, keyEvent))
    }
}

fun MaterialButton.setOnClickListener(listener: () -> Unit) {
    setOnClickListener { listener.invoke() }
}

class EditorAction(private val actionId: Int, private val keyEvent: KeyEvent) {

    private fun isActionDone() = EditorInfo.IME_ACTION_DONE == actionId

    private fun isEnterKeyDownEvent() =
        KeyEvent.KEYCODE_ENTER == keyEvent.keyCode && KeyEvent.ACTION_DOWN == keyEvent.action

    fun isDone(): Boolean = isActionDone() || isEnterKeyDownEvent()
}

class GenericObserver<T>(private val onChange: ((value: T) -> Unit)): Observer<T> {
    override fun onChanged(t: T) {
        onChange.invoke(t)
    }
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChange: ((value: T) -> Unit)) {
    observe(owner, GenericObserver(onChange))
}


abstract class ActivityObserveResult: AppCompatActivity() {

    private val activityResultReference:
            MutableMap<Int, (activityResult: TActivityResult) -> Unit> = mutableMapOf()

    protected fun observeActivityResult(requestCode: Int,
                                        reference: (activityResult: TActivityResult) -> Unit) {
        activityResultReference[requestCode] = reference
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultReference[requestCode]?.invoke(TActivityResult(resultCode, data))
    }
}

class TActivityResult(val requestCode: Int, val data: Intent?) {

    fun isOk() = requestCode == RESULT_OK
}