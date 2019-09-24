package com.faust.m.td.framework.db.room_definition.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao: BaseDao<UserEntity> {

    companion object {
        const val selectAllUsers = "SELECT * FROM users"
        const val orderAsc = "ORDER BY user_id ASC"
    }

    @Query("$selectAllUsers $orderAsc")
    fun getAll(): List<UserEntity>

    @Query("$selectAllUsers WHERE user_id = :id $orderAsc")
    fun get(id: Long): UserEntity?
}