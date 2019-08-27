package com.faust.m.td.activities

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.R
import com.faust.m.td.TranslationApplication
import com.faust.m.td.alertTranslation
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationDao
import kotlinx.android.synthetic.main.activity_input.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class InputActivity: AppCompatActivity(), AnkoLogger {

    private lateinit var translationDao: TranslationDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        initTranslationDao()
        setupTranslateAction()
        setupAddTranslationAction()
        setupCancelAction()
    }
    private fun initTranslationDao () {
        translationDao = TranslationApplication.database!!.translationDao()
    }
    private fun setupTranslateAction() {
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
    private fun setupAddTranslationAction() {
        add_button.setOnClickListener {
            val sentence = sentence_edit_text.text.toString()
            AsyncTask.execute {
                insertSentenceToDatabase(sentence)
                runOnUiThread {
                    finish() }
            }
        }
    }
    private fun insertSentenceToDatabase(sentence: String) {
        val unknownTranslation = Translation(sentence, "unknown!")
        translationDao.insertAll(unknownTranslation)
    }
    private fun setupCancelAction() {
        cancel_button.setOnClickListener {
            finish()
        }
    }
}