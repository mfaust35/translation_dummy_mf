package com.faust.m.td.framework.db.room_definition

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faust.m.td.DATABASE_PATH
import com.faust.m.td.framework.db.room_definition.model.TranslationDao
import com.faust.m.td.framework.db.room_definition.model.TranslationEntity
import com.faust.m.td.framework.db.room_definition.model.UserDao
import com.faust.m.td.framework.db.room_definition.model.UserEntity

@Database(
    entities = [TranslationEntity::class, UserEntity::class],
    version = 2)
abstract class RoomTranslationDatabase : RoomDatabase() {

    abstract fun translationDao(): TranslationDao
    abstract fun userDao(): UserDao

    /**
     * Allow building of a new database instance easily
     */
    companion object {
        @Volatile private var INSTANCE: RoomTranslationDatabase? = null

        fun getInstance(context: Context): RoomTranslationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): RoomTranslationDatabase =
            Room.databaseBuilder(context, RoomTranslationDatabase::class.java, DATABASE_PATH)
                .addMigrations(MIGRATION_1_2)
                .addCallback(PREPOPULATE_V2)
                .build()

    }
}