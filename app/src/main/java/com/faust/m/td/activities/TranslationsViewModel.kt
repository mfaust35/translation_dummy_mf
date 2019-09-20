package com.faust.m.td.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class TranslationsViewModel : ViewModel(), KoinComponent {

    private val translationRepository: TranslationRepository by inject()
    private val allTranslations: LiveData<List<Translation>> =
        translationRepository.getAllTranslation()

    fun getAllTranslation(): LiveData<List<Translation>> {
        println("TranslationViewModel request the translation")
        return allTranslations
    }
    fun insertTranslation(translation: Translation) { translationRepository.insert(translation) }
}