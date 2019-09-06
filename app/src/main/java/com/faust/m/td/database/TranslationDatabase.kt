package com.faust.m.td.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faust.m.td.DATABASE_PATH
import com.faust.m.td.translation.Translation
import com.faust.m.td.translation.TranslationDao
import com.faust.m.td.user.User
import com.faust.m.td.user.UserDao

@Database(
    entities = [Translation::class, User::class],
    version = 2)
abstract class TranslationDatabase : RoomDatabase() {

    abstract fun translationDao(): TranslationDao
    abstract fun userDao(): UserDao

    /**
     * Allow building of a new database instance easily
     */
    companion object {
        @Volatile private var INSTANCE: TranslationDatabase? = null

        fun getInstance(context: Context): TranslationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): TranslationDatabase =
            Room.databaseBuilder(context, TranslationDatabase::class.java, DATABASE_PATH)
                .addMigrations(MIGRATION_1_2)
                .addCallback(PREPOPULATE_V2)
                .build()

    }
}