package com.faust.m.td.data

import com.faust.m.td.domain.User

class UserRepository(private val dataSource: UserDataSource) {

    fun getAllUsers():List<User> = dataSource.getAllUsers()

    fun getUserForId(userId: Long): User? = dataSource.getUserForId(userId)
}