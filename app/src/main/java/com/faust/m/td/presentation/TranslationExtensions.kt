package com.faust.m.td.presentation

import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.R
import com.faust.m.td.Translator
import com.faust.m.td.TranslatorException
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

fun AppCompatActivity.alertTranslation(pSentence: String) {
    if ("EggPlant" == pSentence)
        alertSpecial()
    else {
        val tTranslator = Translator()
        val tTranslatedSentence = try { tTranslator.translate(pSentence) } catch (e: TranslatorException) { null }
        alertTranslatedSentence(pSentence, tTranslatedSentence, tTranslator.toLanguage)
    }
}

private fun AppCompatActivity.alertSpecial() {
    toast("It will come")
}

private fun AppCompatActivity.alertTranslatedSentence(pSentence: String,
                                                      pTranslatedSentence: String?,
                                                      pToLanguage: String) {
    val tTitle = getString(R.string.select_ac_dialog_translate_title)
    val tMessage =
        if (pTranslatedSentence is String)
            getString(R.string.select_ac_dialog_translate_message, pSentence, pToLanguage, pTranslatedSentence)
        else
            when((1..3).shuffled().first()) {
                1 -> getString(R.string.unknown_translation1)
                2 -> getString(R.string.unknown_translation2)
                else -> getString(R.string.unknown_translation3)
            }
    alert(tMessage, tTitle)
    {
        positiveButton(R.string.select_ac_dialog_translate_positive_button) {  }
    }.show()
}