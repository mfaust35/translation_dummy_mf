package com.faust.m.td.translation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class Translation(@ColumnInfo(name="english") var english: String,
                       @ColumnInfo(name="french") var french: String,
                       @ColumnInfo(name="translation_id")
                       @PrimaryKey(autoGenerate = true) var id: Long=0)