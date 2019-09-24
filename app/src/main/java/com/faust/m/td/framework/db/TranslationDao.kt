package com.faust.m.td.framework.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TranslationDao: BaseDao<TranslationEntity> {

    companion object {
        const val selectAllTranslations = "SELECT * FROM translations"
        const val orderAsc = "ORDER BY english ASC"
    }

    @Query("$selectAllTranslations $orderAsc")
    fun getAllTranslations(): List<TranslationEntity>

    @Query("$selectAllTranslations WHERE user_id = :userId $orderAsc")
    fun getAllTranslationsForUser(userId: Long): List<TranslationEntity>
}
