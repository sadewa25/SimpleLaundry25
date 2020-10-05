package com.codedirect.laundry.di

import com.codedirect.laundry.data.source.AppRepository
import com.codedirect.laundry.data.source.remote.RemoteRepository
import com.codedirect.laundry.data.source.remote.RetrofitClient
import com.codedirect.laundry.ui.adding_data.AddingDataVM
import com.codedirect.laundry.ui.home.HomeVM
import com.codedirect.laundry.ui.login.LoginVM
import com.codedirect.laundry.ui.register.RegisterVM
import org.koin.dsl.module

object AppModule {
    val view_models = module {
        single { LoginVM(get()) }
        single { RegisterVM(get()) }
        single { HomeVM(get()) }
        single { AddingDataVM(get()) }
    }


    val repositoryModule = module {
        fun provideRepository(): AppRepository? {
            val remoteRepository =
                RemoteRepository.getInstance(RetrofitClient())
            return remoteRepository.let { localrepo ->
                AppRepository.getInstance(remoteRepository)
            }
        }
        single { provideRepository() }
    }
}