package com.codedirect.laundry.di

import android.app.Application
import com.codedirect.laundry.di.AppModule.repositoryModule
import com.codedirect.laundry.di.AppModule.view_models
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(view_models, repositoryModule))
        }
    }

}