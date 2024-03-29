package com.faust.m.td.framework.db.room_definition.model

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.faust.m.td.framework.db.room_definition.RoomTranslationDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4ClassRunner::class)
class TranslationDaoTest: BaseDaoTest() {

    // Force id to be able to match it in translation
    private val user = UserEntity("moi", 10)
    private val translation = TranslationEntity("hello", "bonjour", user.id, 2)
    private lateinit var translationDao: TranslationDao
    private lateinit var userDao: UserDao

    override fun onDatabaseCreated(database: RoomTranslationDatabase) {
        translationDao = database.translationDao()
        userDao = database.userDao()
    }

    @Test
    fun insertAndGetTranslation() {
        // Given a user in database
        userDao.insertAll(user)

        // When inserting a new translation referencing this user in the database
        translationDao.insertAll(translation)

        // The translation can be retrieved
        translationDao.getAll().apply {
            assertEquals(1, size, "There should be only 1 translation in database")
            assertThat(first()).isEqualTo(translation)
        }
    }

    @Test
    fun insertAndGetTranslationForUser() {
        // Given 2 users in database
        userDao.insertAll(user, UserEntity("another", 12))

        // When inserting 2 new translation each referencing one user
        translationDao.insertAll(
            translation,
            TranslationEntity("test", "test", 12)
        )

        // Only translation belonging to user are retrieved
        translationDao.getAllForUser(10).apply {
            assertThat(size).isEqualTo(1)
            assertThat(first()).isEqualTo(translation)
        }
    }
}