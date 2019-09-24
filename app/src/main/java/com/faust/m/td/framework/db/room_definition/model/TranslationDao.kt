package com.faust.m.td.framework.db.room_definition.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TranslationDao: BaseDao<TranslationEntity> {

    companion object {
        const val selectAllTranslations = "SELECT * FROM translations"
        const val orderAsc = "ORDER BY english ASC"
    }

    @Query("$selectAllTranslations $orderAsc")
    fun getAll(): List<TranslationEntity>

    @Query("$selectAllTranslations WHERE user_id = :userId $orderAsc")
    fun getAllForUser(userId: Long): List<TranslationEntity>
}
