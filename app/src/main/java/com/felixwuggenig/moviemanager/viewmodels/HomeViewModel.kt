package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.models.Movie

class HomeViewModel(
    val dataManager: DataManager,
) : ViewModel() {
    private val mutableMovieData = MutableLiveData<List<Movie>>()
    val movieData: LiveData<List<Movie>> = mutableMovieData
    private val mutableStaffPickData = MutableLiveData<List<Movie>>()
    val staffPickData: LiveData<List<Movie>> = mutableStaffPickData

    fun loadMovieData() {
        mutableMovieData.value = dataManager.getMovieList()
    }

    fun loadStaffPickData() {
        mutableStaffPickData.value = dataManager.getStaffPicks()
    }

}