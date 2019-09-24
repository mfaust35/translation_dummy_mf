package com.faust.m.td.data

import com.faust.m.td.domain.User

interface UserDataSource {

    fun getAllUsers(): List<User>

    fun getUserForId(userId: Long): User?
}