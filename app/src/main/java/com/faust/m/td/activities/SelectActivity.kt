package com.faust.m.td.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faust.m.td.R
import com.faust.m.td.translation.TranslationRecyclerAdapter
import com.faust.m.td.TranslationApplication
import com.faust.m.td.alertTranslation
import com.faust.m.td.translation.TranslationDao
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity: AppCompatActivity() {

    private lateinit var translationDao: TranslationDao
    private lateinit var translationAdapter: TranslationRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        initTranslationDao()
        initAdapterTranslation()
        setupRecyclerView()
    }

    private fun initTranslationDao() {
        translationDao = TranslationApplication.database!!.translationDao()
    }
    private fun initAdapterTranslation() {
        translationAdapter =
            TranslationRecyclerAdapter(emptyList())
        translationAdapter.onItemClickListener = { tSentence: String ->
            when(tSentence) {
                getString(R.string.input_sentence) -> {
                    val intent = Intent(this, InputActivity::class.java)
                    startActivity(intent)
                }
                else -> alertTranslation(tSentence)
            }
        }
    }
    private fun setupRecyclerView() {
        recyclerViewTranslation.layoutManager = LinearLayoutManager(this)
        recyclerViewTranslation.adapter = translationAdapter
    }

    override fun onResume() {
        super.onResume()
        AsyncTask.execute {
            val translations =
                resources.getStringArray(R.array.sentences).toList() +
                        translationDao.getAll().map { it.english }
            translationAdapter.clear()
            translationAdapter.addAll(translations)
            notifyDataChanged()
        }
    }
    private fun notifyDataChanged() {
        runOnUiThread {
            translationAdapter.notifyDataSetChanged()
        }
    }
}
