package com.faust.m.td.framework

import com.faust.m.td.data.TranslationRepository
import com.faust.m.td.data.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule =  module {

    single {
        // Build room database
        RoomTranslationDatabase.getInstance(androidApplication())
    }
    single { get<RoomTranslationDatabase>().translationDao() }
    single { get<RoomTranslationDatabase>().userDao() }
}

val repositoryModule = module {

    single { RoomTranslationDataSource(get()) }
    single { TranslationRepository(get<RoomTranslationDataSource>()) }
    single { RoomUserDataSource(get()) }
    single { UserRepository(get<RoomUserDataSource>()) }
}