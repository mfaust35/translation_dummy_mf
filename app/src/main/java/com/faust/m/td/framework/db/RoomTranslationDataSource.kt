package com.faust.m.td.framework.db

import com.faust.m.td.data.TranslationDataSource
import com.faust.m.td.domain.Translation
import com.faust.m.td.framework.db.room_definition.model.TranslationDao
import com.faust.m.td.framework.db.room_definition.model.TranslationEntity

class RoomTranslationDataSource(private val translationDao: TranslationDao) : TranslationDataSource {

    override fun addTranslation(translation: Translation) =
        translationDao.insertAll(translation.toEntityModel())


    override fun getAllTranslations(): List<Translation> =
        translationDao.getAll().map(::toDomainModel)


    override fun getAllTranslationsForUser(userId: Long): List<Translation> =
        translationDao.getAllForUser(userId).map(::toDomainModel)


    private fun toDomainModel(translation: TranslationEntity): Translation =
        Translation(translation.english, translation.french, translation.userId, translation.id)

    private fun Translation.toEntityModel(): TranslationEntity =
        TranslationEntity(english, french, userId, id)
}