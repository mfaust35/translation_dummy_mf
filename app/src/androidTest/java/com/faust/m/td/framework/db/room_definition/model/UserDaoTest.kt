package com.faust.m.td.framework.db.room_definition.model

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.faust.m.td.framework.db.room_definition.RoomTranslationDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserDaoTest: BaseDaoTest() {

    private val user = UserEntity("test", 10)
    private lateinit var userDao: UserDao

    override fun onDatabaseCreated(database: RoomTranslationDatabase) {
        userDao = database.userDao()
    }

    @Test
    fun insertAndGetUser() {
        // Whe inserting a new user in the database
        userDao.insertAll(user)

        // The user can be retrieved
        userDao.get(user.id).apply {
            assertThat(this).`as`("User from db").isEqualTo(user)
        }
    }
}