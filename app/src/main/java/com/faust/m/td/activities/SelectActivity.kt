package com.faust.m.td.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.R
import com.faust.m.td.Translator
import kotlinx.android.synthetic.main.activity_select.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class SelectActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val tSentences:Array<String> = resources.getStringArray(R.array.sentences)
        val tAdapter = ArrayAdapter(this, R.layout.select_sentence_item, tSentences)

        selectListViewSentences.adapter = tAdapter
        selectListViewSentences.setOnItemClickListener { _, _, position, _ ->
            val tSentence = tAdapter.getItem(position) as String
            when (tSentence) {
                getString(R.string.input_sentence) -> toast("This should display a custom input activity")
                else -> {
                    val tTranslator = Translator()
                    val tTranslation = tTranslator.translate(tSentence)
                    showTranslation(tSentence, tTranslation, tTranslator.toLanguage)
                }
            }
        }
    }

    private fun showTranslation(pSentence: String, pTranslation: String, pToLanguage: String) {
        val tTitle = getString(R.string.select_ac_dialog_translate_title)
        val tMessage = getString(R.string.select_ac_dialog_translate_message,
            pSentence, pToLanguage, pTranslation)
        alert(tMessage, tTitle) {
            positiveButton(R.string.select_ac_dialog_translate_positive_button) {  }
        }.show()
    }
}