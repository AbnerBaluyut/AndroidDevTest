package com.example.sampleapp.app

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.example.sampleapp.app.di.*
import com.example.sampleapp.common.utils.Prefs
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Kotpref.init(this)
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule, repositoryModule, netModule, apiModule, databaseModule))
        }
    }
}