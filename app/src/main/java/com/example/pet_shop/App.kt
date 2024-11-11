package com.example.pet_shop

import android.app.Application
import com.example.pet_shop.di.appModule
import com.example.pet_shop.di.useCases
import com.example.pet_shop.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.INFO)
            modules(appModule, useCases, viewModelModule)
        }
    }
}