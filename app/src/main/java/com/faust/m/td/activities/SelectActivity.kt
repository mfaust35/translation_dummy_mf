package com.faust.m.td.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.R
import com.faust.m.td.alertTranslation
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationRecyclerAdapter
import com.faust.m.td.user.UserDao
import kotlinx.android.synthetic.main.activity_select.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class SelectActivity: AppCompatActivity() {

    private val userDao: UserDao by inject()
    private lateinit var translationAdapter: TranslationRecyclerAdapter
    private lateinit var viewModel: TranslationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        viewModel = ViewModelProviders.of(this).get(TranslationsViewModel::class.java)

        println("Start observing the translations")
        viewModel.getAllTranslation().observe(this, ObserverTranslations())

        println("Initialize adapter")
        // Initialize adapter
        translationAdapter = TranslationRecyclerAdapter(onItemClick = ::onTranslationClick)
        // Setup recyclerView
        recyclerViewTranslation.layoutManager = LinearLayoutManager(this)
        recyclerViewTranslation.adapter = translationAdapter
    }

    inner class ObserverTranslations: Observer<List<Translation>> {
        override fun onChanged(translations: List<Translation>?) {
            println("It changed")
            val test = resources.getStringArray(R.array.sentences).toMutableList()
            translations?.let {
                test += it.map(Translation::english)
            }
            translationAdapter.resetTranslationsTo(test)
        }

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
            // Get current user and load translation
            val currentUser = userDao.get(DEFAULT_USER_ID)
            runOnUiThread {
                // Toast current user name and show translation
                toast("Welcome back ${currentUser?.username ?: "USER_NOT_FOUND"}")
            }
        }
    }
}
