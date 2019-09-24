package com.faust.m.td.presentation.select

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.faust.m.td.R
import com.faust.m.td.domain.Translation
import com.faust.m.td.domain.User
import com.faust.m.td.presentation.ActivityObserveResult
import com.faust.m.td.presentation.TActivityResult
import com.faust.m.td.presentation.alertTranslation
import com.faust.m.td.presentation.input.InputActivity
import com.faust.m.td.presentation.observe
import kotlinx.android.synthetic.main.activity_select.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

const val INPUT_TRANSLATION = 1

class SelectActivity: ActivityObserveResult(), AnkoLogger  {

    private lateinit var translationAdapter: TranslationRecyclerAdapter
    private lateinit var viewModel: TranslationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        // Initialize adapter for recyclerView
        translationAdapter = TranslationRecyclerAdapter(onItemClick = ::onTranslationClick)
        // Setup recyclerView
        recyclerViewTranslation.layoutManager = LinearLayoutManager(this)
        recyclerViewTranslation.adapter = translationAdapter

        // Initialize observe activityResult
        observeActivityResult(INPUT_TRANSLATION, ::onInputTranslationResult)

        // Initialize view model
        viewModel = ViewModelProviders.of(this).get(TranslationsViewModel::class.java)
        viewModel.getAllTranslations().observe(this, ::onTranslationsChanged)
        viewModel.getCurrentUser().observe(this, ::onCurrentUserChanged)
    }

    private fun onTranslationClick(value: String) {
        when(value) {
            getString(R.string.input_sentence) -> {
                val intent = Intent(this, InputActivity::class.java)
                startActivityForResult(intent, INPUT_TRANSLATION)
            }
            else -> alertTranslation(value)
        }
    }

    private fun onTranslationsChanged(translations: List<Translation>) {
        val sentences =
            resources.getStringArray(R.array.sentences).toMutableList() +
                    translations.map(Translation::english)
        translationAdapter.resetTranslationsTo(sentences)
        translationAdapter.notifyDataSetChanged()
    }

    private fun onCurrentUserChanged(currentUser: User) {
        toast("Welcome back ${currentUser.username}")
    }

    private fun onInputTranslationResult(result: TActivityResult) {
        when {
            result.isOk() -> viewModel.refreshTranslation()
        }
    }
}
