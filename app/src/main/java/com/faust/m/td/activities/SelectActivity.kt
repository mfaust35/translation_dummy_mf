package com.faust.m.td.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.faust.m.td.R
import com.faust.m.td.alertTranslation
import kotlinx.android.synthetic.main.activity_select.*

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
                getString(R.string.input_sentence) -> {
                    val intent = Intent(this, InputActivity::class.java)
                    startActivity(intent)
                }
                else -> alertTranslation(tSentence)
            }
        }
    }
}