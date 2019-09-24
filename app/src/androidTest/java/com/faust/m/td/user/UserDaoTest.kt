package com.faust.m.td.user

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.faust.m.td.database.BaseDaoTest
import com.faust.m.td.framework.RoomTranslationDatabase
import com.faust.m.td.framework.db.UserEntity
import com.faust.m.td.framework.db.UserDao
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        val dbUser = userDao.getUserFor(user.id)
        assertNotNull(dbUser)
        assertEquals(dbUser.username, user.username)
        assertEquals(dbUser.id, user.id)
    }
}