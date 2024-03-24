package com.felixwuggenig.moviemanager.di

import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.data.SharedPreferences
import com.felixwuggenig.moviemanager.models.typeadapter.LocalDateTypeAdapter
import com.felixwuggenig.moviemanager.viewmodels.HomeViewModel
import com.felixwuggenig.moviemanager.viewmodels.LoginViewModel
import com.felixwuggenig.moviemanager.viewmodels.MainViewModel
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import java.time.LocalDate

val appModule = module {
    single {
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
            .create()
    }
    single { DataManager(gson = get(), resources = androidContext().resources) }
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    single<SharedPreferences> { SharedPreferences(androidContext()) }
}