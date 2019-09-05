package com.faust.m.td.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BaseDao<T> {

    @Insert
    fun insertAll(vararg objects: T)
}