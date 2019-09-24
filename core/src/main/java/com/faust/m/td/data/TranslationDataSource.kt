package com.faust.m.td.data

import com.faust.m.td.domain.Translation

interface TranslationDataSource {

    fun addTranslation(translation: Translation)

    fun getAllTranslations(): List<Translation>

    fun getAllTranslationsForUser(userId: Long): List<Translation>
}