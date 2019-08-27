package com.faust.m.td.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faust.m.td.R
import com.faust.m.td.RecyclerAdapterTranslation
import com.faust.m.td.alertTranslation
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        recyclerViewTranslation.layoutManager = LinearLayoutManager(this)
        recyclerViewTranslation.adapter = translationAdapter()
    }
    private fun translationAdapter(): RecyclerAdapterTranslation {
        val rTranslationAdapter =
            RecyclerAdapterTranslation(resources.getStringArray(R.array.sentences).toList())
        rTranslationAdapter.onItemClickListener = { tSentence: String ->
            when(tSentence) {
                getString(R.string.input_sentence) -> {
                    val intent = Intent(this, InputActivity::class.java)
                    startActivity(intent)
                }
                else -> alertTranslation(tSentence)
            }
        }
        return rTranslationAdapter
    }
}