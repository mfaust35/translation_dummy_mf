package com.faust.m.td.framework.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao: BaseDao<UserEntity> {

    companion object {
        const val selectAllUsers = "SELECT * FROM users"
        const val orderAsc = "ORDER BY user_id ASC"
    }

    @Query("$selectAllUsers")
    fun getAllUsers(): List<UserEntity>

    @Query("$selectAllUsers WHERE user_id = :id $orderAsc")
    fun getUserFor(id: Long): UserEntity?
}