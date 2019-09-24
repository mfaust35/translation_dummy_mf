package com.faust.m.td.framework

import com.faust.m.td.data.TranslationDataSource
import com.faust.m.td.domain.Translation
import com.faust.m.td.framework.db.TranslationDao
import com.faust.m.td.framework.db.TranslationEntity
import org.koin.core.KoinComponent
import org.koin.core.inject

class RoomTranslationDataSource(private val translationDao: TranslationDao) : TranslationDataSource {

    override fun addTranslation(translation: Translation) =
        translationDao.insertAll(translation.toEntityModel())


    override fun getAllTranslations(): List<Translation> =
        translationDao.getAllTranslations().map(::toDomainModel)


    override fun getAllTranslationsForUser(userId: Long): List<Translation> =
        translationDao.getAllTranslationsForUser(userId).map(::toDomainModel)


    private fun toDomainModel(translation: TranslationEntity): Translation =
        Translation(translation.english, translation.french, translation.userId, translation.id)

    private fun Translation.toEntityModel(): TranslationEntity =
        TranslationEntity(english, french, userId, id)
}