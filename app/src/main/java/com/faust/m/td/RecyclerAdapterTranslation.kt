package com.faust.m.td

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.find

class RecyclerAdapterTranslation(values: Collection<String>) :
     RecyclerView.Adapter<RecyclerAdapterTranslation.TranslationHolder>() {

    private val mTranslations: MutableList<String> = ArrayList(values)
    var onItemClickListener: ((tSentence: String) -> Unit)? = null

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
            view.setOnClickListener { onItemClickListener?.invoke(translation) }
        }
    }
}