package com.faust.m.td.translation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TranslationDao {

    @Query("SELECT * FROM translations")
    fun getAll(): List<Translation>

    @Insert
    fun insertAll(vararg translation: Translation)
}
