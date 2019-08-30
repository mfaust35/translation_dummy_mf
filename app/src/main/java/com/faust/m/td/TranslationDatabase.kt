package com.faust.m.td

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationDao

@Database(
    entities = [Translation::class],
    version = 1)
abstract class TranslationDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
}
