package com.faust.m.td.framework.db

import com.faust.m.td.data.UserDataSource
import com.faust.m.td.domain.User
import com.faust.m.td.framework.db.room_definition.model.UserDao
import com.faust.m.td.framework.db.room_definition.model.UserEntity

class RoomUserDataSource(private val userDao: UserDao) : UserDataSource {

    override fun getAllUsers(): List<User> = userDao.getAll().map { it.toDomainModel() }

    override fun getUserForId(userId: Long): User? {
        return userDao.get(userId)?.toDomainModel()
    }


    private fun UserEntity.toDomainModel(): User = User(username, id)
}


