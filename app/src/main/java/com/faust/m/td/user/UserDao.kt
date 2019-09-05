package com.faust.m.td.user

import androidx.room.Dao
import androidx.room.Query
import com.faust.m.td.database.BaseDao

@Dao
interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE user_id = :id")
    fun get(id: Long): User?
}