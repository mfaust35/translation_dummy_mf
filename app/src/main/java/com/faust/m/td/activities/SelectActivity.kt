package com.faust.m.td.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faust.m.td.R
import com.faust.m.td.alertTranslation
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationDao
import com.faust.m.td.translation.TranslationRecyclerAdapter
import kotlinx.android.synthetic.main.activity_select.*
import org.koin.android.ext.android.inject

class SelectActivity: AppCompatActivity() {

    private val translationDao: TranslationDao by inject()
    private lateinit var translationAdapter: TranslationRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        // Initialize adapter
        translationAdapter = TranslationRecyclerAdapter(onItemClick = ::onTranslationClick)
        // Setup recyclerView
        recyclerViewTranslation.layoutManager = LinearLayoutManager(this)
        recyclerViewTranslation.adapter = translationAdapter
    }

    private fun onTranslationClick(value: String) {
        when(value) {
            getString(R.string.input_sentence) -> {
                val intent = Intent(this, InputActivity::class.java)
                startActivity(intent)
            }
            else -> alertTranslation(value)
        }
    }

    override fun onResume() {
        super.onResume()
        AsyncTask.execute {
            translationAdapter.resetTranslationsTo(
                resources.getStringArray(R.array.sentences).toList() +
                        translationDao.getAll().map(Translation::english))
            runOnUiThread {
                translationAdapter.notifyDataSetChanged()
            }
        }
    }
}
