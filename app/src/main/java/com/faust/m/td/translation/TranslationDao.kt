package com.faust.m.td.translation

import androidx.room.Dao
import androidx.room.Query
import com.faust.m.td.database.BaseDao

@Dao
interface TranslationDao: BaseDao<Translation> {

    @Query("SELECT * FROM translations")
    fun getAll(): List<Translation>

    @Query("SELECT * FROM translations WHERE user_id = :userId")
    fun getAllForUser(userId: Long): List<Translation>
}
