package com.felixwuggenig.moviemanager.di

import com.felixwuggenig.moviemanager.DataManager
import com.felixwuggenig.moviemanager.MainViewModel
import com.felixwuggenig.moviemanager.models.typeadapter.LocalDateTypeAdapter
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import java.time.LocalDate

val appModule = module {
    // Define your dependencies here
    // Example:
    // single { MyRepository() }
    single {
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
            .create()
    }
    single { DataManager(gson = get(), resources = androidContext().resources) }
    viewModelOf(::MainViewModel)
}