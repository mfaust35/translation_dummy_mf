package com.faust.m.td.translation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.faust.m.td.user.User

@Entity(
    tableName = "translations",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class Translation(@ColumnInfo(name = "english") var english: String,
                       @ColumnInfo(name = "french") var french: String,
                       @ColumnInfo(name = "user_id") var userId: Long,
                       @ColumnInfo(name = "translation_id")
                       @PrimaryKey(autoGenerate = true) var id: Long=0)