package com.faust.m.td.data

import com.faust.m.td.domain.Translation

class TranslationRepository(private val dataSource: TranslationDataSource) {

    fun addTranslation(translation: Translation) =
        dataSource.addTranslation(translation)

    fun getAllTranslations(): List<Translation> =
        dataSource.getAllTranslations()

    fun getAllTranslationsForUser(userId: Long): List<Translation> =
        dataSource.getAllTranslationsForUser(userId)
}