package com.example.training4

import android.app.Application
import com.example.training4.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HabitFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HabitFlowApp)
            modules(appModule)
        }
    }
}
