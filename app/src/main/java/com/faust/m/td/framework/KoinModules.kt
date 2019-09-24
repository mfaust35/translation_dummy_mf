package com.faust.m.td.framework

import com.faust.m.td.data.TranslationDataSource
import com.faust.m.td.data.UserDataSource
import com.faust.m.td.framework.db.RoomTranslationDataSource
import com.faust.m.td.framework.db.room_definition.RoomTranslationDatabase
import com.faust.m.td.framework.db.RoomUserDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule =  module {

    single {
        // Build room database
        RoomTranslationDatabase.getInstance(androidApplication())
    }

    single {
        RoomTranslationDataSource(get<RoomTranslationDatabase>().translationDao())
    } bind TranslationDataSource::class

    single {
        RoomUserDataSource(get<RoomTranslationDatabase>().userDao())
    } bind UserDataSource::class
}