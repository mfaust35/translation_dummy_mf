package com.faust.m.td

import android.app.Application
import androidx.room.Room

class TranslationApplication : Application() {

    companion object {
        var database: TranslationDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        TranslationApplication.database = Room.databaseBuilder(this,
                TranslationDatabase::class.java,
                "translations_path")
            .build()
    }
}