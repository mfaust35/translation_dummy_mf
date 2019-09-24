package com.faust.m.td.presentation.input

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.R
import com.faust.m.td.framework.db.TranslationDao
import com.faust.m.td.framework.db.TranslationEntity
import com.faust.m.td.presentation.*
import kotlinx.android.synthetic.main.activity_input.*
import org.koin.android.ext.android.inject

class InputActivity: AppCompatActivity() {

    private val translationDao: TranslationDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        sentence_edit_text.setOnEditorActionListener(::onSentenceEtEA)
        translate_button.setOnClickListener(::onTranslateBtnC)
        add_translation_button.setOnClickListener(::onAddTranslationBtnC)
        cancel_button.setOnClickListener(::onCancelBtnC)
    }

    private fun onSentenceEtEA(textView: TextView, editorAction: EditorAction): Boolean {
        if (editorAction.isDone()) {
            alertTranslation(textView.text.toString())
            clearSoftInputFocus(sentence_edit_text)
            return true
        }
        return false
    }

    private fun onTranslateBtnC() {
        alertTranslation(sentence_edit_text.text.toString())
        clearSoftInputFocus(sentence_edit_text)
    }

    private fun onAddTranslationBtnC() {
        val sentence = sentence_edit_text.text.toString()
        AsyncTask.execute {
            // TODO replace user_id by real user id when feature choose use is created
            // For now, use DEFAULT_USER_ID
            translationDao.insertAll(
                TranslationEntity(
                    sentence,
                    "unknown!",
                    DEFAULT_USER_ID
                )
            )
            runOnUiThread {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun onCancelBtnC() {
        finish()
    }
}