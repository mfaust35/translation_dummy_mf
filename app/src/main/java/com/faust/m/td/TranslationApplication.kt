package com.faust.m.td

import android.app.Application
import com.faust.m.td.koin.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranslationApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TranslationApplication)
            modules(listOf(databaseModule))
        }
    }
}