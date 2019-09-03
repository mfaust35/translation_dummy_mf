package com.faust.m.td.translation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faust.m.td.R
import com.faust.m.td.inflate
import org.jetbrains.anko.find


class TranslationRecyclerAdapter(translations: Collection<String>? = null,
                                 var onItemClick: ((value: String) -> Unit)? = null):
    RecyclerView.Adapter<TranslationRecyclerAdapter.TranslationHolder>() {

    private val mTranslations: MutableList<String> =
        if (translations.isNullOrEmpty()) mutableListOf() else ArrayList(translations)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationHolder {
        val inflatedView = parent.inflate(R.layout.recycler_view_translation, false)
        return TranslationHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mTranslations.size
    }

    override fun onBindViewHolder(holder: TranslationHolder, position: Int) {
        val translation = mTranslations[position]
        holder.bindTranslation(translation)
    }

    private fun addAll(sentences: Collection<String>) {
        // TODO : I don't believe I can modify the list just like that
        // What happens in case the UI thread is currently trying to display the list?
        this.mTranslations.addAll(sentences)
    }

    private fun clear() {
        // TODO : cf TODO on addAll() method
        mTranslations.clear()
    }

    fun resetTranslationsTo(sentences: Collection<String>) {
        clear()
        addAll(sentences)
    }

    inner class TranslationHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private var highlight: TextView = view.find(R.id.recycler_view_translation_highlight)
        private var sentence: TextView = view.find(R.id.recycler_view_translation_sentence)

        fun bindTranslation(translation: String) {
            displayTranslation(translation)
            registerOnClickListener(translation)
        }
        private fun displayTranslation(translation: String) {
            when {
                translation.isNotEmpty() -> {
                    highlight.text = translation.subSequence(0, 1)
                    sentence.text = translation }
                else -> {
                    // TODO: memory impact `get string from context on each bind` vs `initialize strings once`
                    highlight.text = highlight.context.getString(R.string.select_ac_recycler_view_translation_no_highlight)
                    sentence.text = sentence.context.getString(R.string.select_ac_recycler_view_translation_no_sentence) }
            }
        }
        private fun registerOnClickListener(translation: String) {
            view.setOnClickListener { onItemClick?.invoke(translation) }
        }
    }
}