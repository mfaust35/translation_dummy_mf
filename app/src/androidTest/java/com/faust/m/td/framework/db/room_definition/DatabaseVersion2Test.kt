package com.faust.m.td.framework.db.room_definition

import android.database.Cursor
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.faust.m.td.DEFAULT_USER_ID
import com.faust.m.td.framework.db.room_definition.model.TranslationEntity
import com.faust.m.td.framework.db.room_definition.model.UserEntity
import com.faust.m.td.framework.db.room_definition.model.getLongFrom
import com.faust.m.td.framework.db.room_definition.model.getStringFrom
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DB_NAME = "migration-test"

@RunWith(AndroidJUnit4ClassRunner::class)
class DatabaseVersion2Test {

    private val defaultUserEntity = UserEntity("mldie35", DEFAULT_USER_ID)

    @get:Rule
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        RoomTranslationDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory())

    @get:Rule
    val creationTestHelper = CreationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        RoomTranslationDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrateVersion1To2KeepTranslations() {
        // Given a database with version 1 schema and 1 translation
        migrationTestHelper.createDatabase(TEST_DB_NAME, 1).apply {
            execSQL("INSERT INTO translations VALUES ('hello', 'bonjour', '10')")
        }

        // When applying migration from version 1 to 2
        val db = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB_NAME, 2, true, MIGRATION_1_2
        )

        // The translation has been migrated and contain the DEFAULT_USER_ID
        db.query("SELECT * FROM translations").apply {
            assertThatRowNumberIsEqualTo(1)
            moveToFirst()
            assertThat(this.toTranslationEntity())
                .`as`("TranslationEntity from cursor")
                .isEqualTo(
                    TranslationEntity("hello", "bonjour", DEFAULT_USER_ID, 10)
                )
        }
    }

    @Test
    fun migrateVersion1To2InsertDefaultUser() {
        // Given a database with version 1 schema
        migrationTestHelper.createDatabase(TEST_DB_NAME, 1)

        // When applying migration from version 1 to 2
        val db = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB_NAME, 2, true, MIGRATION_1_2
        )

        // The table user contain 1 user with default values
        db.query("SELECT * FROM users").apply {
            assertThatRowNumberIsEqualTo(1)
            moveToFirst()
            assertThat(this.toUserEntity())
                .`as`("UserEntity from cursor")
                .isEqualTo(defaultUserEntity)
        }
    }

    @Test
    fun createVersion2InsertDefaultUser() {
        // When database is created on version 2
        val db =
            creationTestHelper.createDatabase(TEST_DB_NAME, 2, listOf(PREPOPULATE_V2))

        // The default user can be retrieve
        db.query("SELECT * FROM users").apply {
            assertThatRowNumberIsEqualTo(1)
            moveToFirst()
            assertThat(this.toUserEntity())
                .`as`("UserEntity from cursor")
                .isEqualTo(defaultUserEntity)
        }
    }


    private fun Cursor.assertThatRowNumberIsEqualTo(expected: Int) {
        assertThat(count).`as`("Number of rows in cursor").isEqualTo(expected)
    }

    private fun Cursor.toTranslationEntity(): TranslationEntity = TranslationEntity(
        getStringFrom("english"),
        getStringFrom("french"),
        getLongFrom("user_id"),
        getLongFrom("translation_id")
    )

    private fun Cursor.toUserEntity(): UserEntity = UserEntity(
        getStringFrom("username"),
        getLongFrom("user_id")
    )
}