package com.faust.m.td.activities

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.R
import com.faust.m.td.alertTranslation
import kotlinx.android.synthetic.main.activity_input.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class InputActivity: AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        sentence_edit_text.setOnEditorActionListener { textView, actionId, keyEvent ->
            var handled = false
            if (isActionDone(actionId) || isEnterKeyDownEvent(keyEvent)) {
                val sentence = textView.text.toString()
                info { "sentence = \"$sentence\"" }
                alertTranslation(sentence)
                clearWindowForAlert()
                handled = true
            }
            handled
        }

        translate_button.setOnClickListener {
            val sentence = sentence_edit_text.text.toString()
            alertTranslation(sentence)
            clearWindowForAlert()
        }

        cancel_button.setOnClickListener {
            finish()
        }
    }

    private fun isActionDone(actionId: Int?): Boolean {
        return EditorInfo.IME_ACTION_DONE == actionId
    }

    private fun isEnterKeyDownEvent(keyEvent: KeyEvent?): Boolean {
        return KeyEvent.KEYCODE_ENTER == keyEvent?.keyCode && KeyEvent.ACTION_DOWN == keyEvent.action
    }

    private fun clearWindowForAlert() {
        sentence_edit_text.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(sentence_edit_text.windowToken, 0)
    }
}