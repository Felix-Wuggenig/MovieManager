package com.felixwuggenig.moviemanager.di

import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.data.SharedPreferences
import com.felixwuggenig.moviemanager.models.typeadapter.LocalDateTypeAdapter
import com.felixwuggenig.moviemanager.viewmodels.DetailsViewModel
import com.felixwuggenig.moviemanager.viewmodels.HomeViewModel
import com.felixwuggenig.moviemanager.viewmodels.SignUpViewModel
import com.felixwuggenig.moviemanager.viewmodels.MainViewModel
import com.felixwuggenig.moviemanager.viewmodels.SearchViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import java.time.LocalDate

val appModule = module {
    single<Gson> {
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
            .create()
    }
    single<DataManager> { DataManager(gson = get(), resources = androidContext().resources) }
    single<SharedPreferences> { SharedPreferences(context = androidContext(), gson = get()) }

    viewModelOf(::MainViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
}