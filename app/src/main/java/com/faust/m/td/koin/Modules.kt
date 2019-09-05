package com.faust.m.td.koin

import com.faust.m.td.database.TranslationDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule =  module {

    single {
        // Build room database
        TranslationDatabase.getInstance(androidApplication())
    }
    single { get<TranslationDatabase>().translationDao() }
    single { get<TranslationDatabase>().userDao() }
}
