package com.faust.m.td.framework.db.room_definition

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.framework.db.room_definition.model.UserEntity

private val DEFAULT_USER_V2 = UserEntity("mldie35", DEFAULT_USER_ID)

private fun prepopulateDatabaseVersion2(database: SupportSQLiteDatabase) {
    // Insert default user
    database.execSQL("""
            INSERT INTO users
                VALUES ('${DEFAULT_USER_V2.username}', '${DEFAULT_USER_V2.id}')
        """.trimIndent())
}

/**
 * Callback to prepopulate database on version 2
 */
val PREPOPULATE_V2 = object : RoomDatabase.Callback() {
    // Prepopulate database during its creation
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        prepopulateDatabaseVersion2(db)
    }
}

/**
 * Migrate from:
 * version 1 - have table {@link TranslationEntity}
 * to
 * version 2 - add a table {@link UserEntity} with a default user and add foreign key into table
 * {@link TranslationEntity} referencing the {@link UserEntity#id}
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create the new `users` table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `users` (
                `username` TEXT NOT NULL,
                `user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)
        """.trimIndent())

        // Insert a default user
        prepopulateDatabaseVersion2(database)

        // Next 4 steps correspond to adding a foreign key into table translation
        // All existing translations will now have DEFAULT_USER_ID as user_id
        // 1. Create a temp translation table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `translations_temp` (
                `english` TEXT NOT NULL,
                `french` TEXT NOT NULL,
                `user_id` INTEGER NOT NULL,
                `translation_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            FOREIGN KEY(`user_id`)
                REFERENCES `users`(`user_id`)
                ON UPDATE NO ACTION
                ON DELETE CASCADE )
        """.trimIndent())
        // 2. Copy the data
        database.execSQL("""
            INSERT INTO `translations_temp` (english, french, user_id, translation_id)
                SELECT english, french, $DEFAULT_USER_ID, translation_id
                FROM translations
        """.trimIndent())
        // 3. Remove old table
        database.execSQL("DROP TABLE translations")
        // 4. Change name of table to correct one
        database.execSQL("ALTER TABLE translations_temp RENAME TO translations")
    }
}