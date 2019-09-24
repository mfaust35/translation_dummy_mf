package com.faust.m.td.framework

import com.faust.m.td.data.UserDataSource
import com.faust.m.td.domain.User
import com.faust.m.td.framework.db.UserDao
import com.faust.m.td.framework.db.UserEntity

class RoomUserDataSource(private val userDao: UserDao) : UserDataSource {

    override fun getAllUsers(): List<User> = userDao.getAllUsers().map { it.toDomainModel() }

    override fun getUserForId(userId: Long): User? {
        return userDao.getUserFor(userId)?.toDomainModel()
    }


    private fun UserEntity.toDomainModel(): User = User(username, id)
}


