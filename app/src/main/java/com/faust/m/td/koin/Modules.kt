package com.faust.m.td.koin

import androidx.room.Room
import com.faust.m.td.DATABASE_PATH
import com.faust.m.td.TranslationDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule =  module {

    single {
        // Build room database
        Room.databaseBuilder(
                androidApplication(),
                TranslationDatabase::class.java,
                DATABASE_PATH)
            .build()
    }
    single { get<TranslationDatabase>().translationDao() }
}
