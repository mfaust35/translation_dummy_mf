package com.faust.m.td.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "translations",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class TranslationEntity(@ColumnInfo(name = "english") var english: String,
                             @ColumnInfo(name = "french") var french: String,
                             @ColumnInfo(name = "user_id") var userId: Long,
                             @ColumnInfo(name = "translation_id")
                             @PrimaryKey(autoGenerate = true) var id: Long=0)