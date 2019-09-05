package com.faust.m.td.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(@ColumnInfo(name = "username") var username: String,
                @ColumnInfo(name = "user_id")
                @PrimaryKey(autoGenerate = true) var id: Long=0)