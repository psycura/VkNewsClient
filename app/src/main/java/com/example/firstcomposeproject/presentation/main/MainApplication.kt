package com.example.firstcomposeproject.presentation.main

import android.app.Application
import com.example.firstcomposeproject.di.NewsAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(NewsAppModule().module)
        }
    }
}