package com.faust.m.td.activities

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.*
import com.faust.m.td.data.TranslationDataSource
import com.faust.m.td.domain.Translation
import kotlinx.android.synthetic.main.activity_input.*
import org.koin.android.ext.android.inject

class InputActivity: AppCompatActivity() {

    private val translationDS: TranslationDataSource by inject()

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

    private fun onTranslateBtnC(v: View?) {
        alertTranslation(sentence_edit_text.text.toString())
        clearSoftInputFocus(sentence_edit_text)
    }

    private fun onAddTranslationBtnC(v: View) {
        val sentence = sentence_edit_text.text.toString()
        AsyncTask.execute {
            // TODO replace user_id by real user id when feature choose use is created
            // For now, use DEFAULT_USER_ID
            translationDS.addTranslation(Translation(sentence, "unknown!", DEFAULT_USER_ID))
            runOnUiThread {
                finish() }
        }
    }

    private fun onCancelBtnC(v: View) {
        finish()
    }
}