package com.faust.m.td.presentation.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.data.TranslationRepository
import com.faust.m.td.data.UserRepository
import com.faust.m.td.domain.Translation
import com.faust.m.td.domain.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import org.koin.core.KoinComponent
import org.koin.core.inject

class TranslationsViewModel : ViewModel(), KoinComponent, AnkoLogger {

    private val translationRepository: TranslationRepository by inject()
    private val userRepository: UserRepository by inject()

    private val allTranslations =
        MutableLiveData<List<Translation>>().apply {
            refreshTranslation()
        }
    private val currentUser = MutableLiveData<User>().apply {
        GlobalScope.launch {
            postValue(userRepository.getUserForId(DEFAULT_USER_ID))
        }
    }


    fun getAllTranslations(): LiveData<List<Translation>> = allTranslations

    fun refreshTranslation() {
        warn { "We are refreshing the transaction list ... fresh paint" }
        GlobalScope.launch {
            allTranslations.postValue(translationRepository.getAllTranslations())
        }
    }

    fun addTranslation(translation: Translation) =
        GlobalScope.launch {
            translationRepository.addTranslation(translation)
        }

    fun getCurrentUser(): LiveData<User> = currentUser
}