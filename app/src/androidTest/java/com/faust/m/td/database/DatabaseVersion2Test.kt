package com.faust.m.td.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.faust.m.td.DEFAULT_USER_ID
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val TEST_DB_NAME = "migration-test"

@RunWith(AndroidJUnit4ClassRunner::class)
class DatabaseVersion2Test {

    private val defaultUsername = "mldie35"
    private val defaultUserId = 1.toLong()

    @get:Rule
    val migrationTestHelper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            TranslationDatabase::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory()
        )

    @Test
    fun migrateVersion1To2KeepTranslations() {
        // Given a database with version 1 schema and 1 translation
        migrationTestHelper.createDatabase(TEST_DB_NAME, 1).apply {
            execSQL("INSERT INTO translations VALUES ('hello', 'bonjour', '10')")
            close()
        }

        // When applying migration from version 1 to 2
        val db = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB_NAME, 2, true, MIGRATION_1_2
        )

        // The translation has been migrated and contain the DEFAULT_USER_ID
        db.query("SELECT * FROM translations").apply {
            moveToFirst()
            assertTrue(isLast, "There should be only one row in database")
            assertEquals("hello", getStringFrom("english"))
            assertEquals("bonjour", getStringFrom("french"))
            assertEquals(DEFAULT_USER_ID, getLongFrom("user_id"))
            assertEquals(10, getLongFrom("translation_id"))
        }
    }

    @Test
    fun migrateVersion1To2InsertDefaultUser() {
        // Given a database with version 1 schema
        migrationTestHelper.createDatabase(TEST_DB_NAME, 1).apply { close() }

        // When applying migration from version 1 to 2
        val db = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB_NAME, 2, true, MIGRATION_1_2
        )

        // The table user contain 1 user with default values
        db.query("SELECT * FROM users").apply {
            assertEquals(1, count, "There should be only one row in the database")
            moveToFirst()
            assertEquals(defaultUsername, getStringFrom("username"))
            assertEquals(defaultUserId, getLongFrom("user_id"))
            close()
        }
    }

    /*
    Unable to test that yet, we need a helper for createDatabase with a callback? argument
    @Test
    fun createVersion2InsertDefaultUser() {
        // When database is created on version 2
        val db =
            migrationTestHelper.createDatabase(TEST_DB_NAME, 2)

        // The default user can be retrieve
        db.query("SELECT * FROM users").apply {
            assertEquals(1, count, "There should be only one row in database")
            moveToFirst()
            assertEquals(defaultUsername, getStringFrom("username"))
            assertEquals(defaultUserId, getLongFrom("user_id"))
            close()
        }
    }*/
}