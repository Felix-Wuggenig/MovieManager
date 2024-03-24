package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.models.Movie

class MainViewModel(
    val dataManager: DataManager,
) : ViewModel() {
    private val movieData = MutableLiveData<List<Movie>>()
    val mutableMovieData: LiveData<List<Movie>> = movieData

    fun loadMovieData() {
        movieData.value = dataManager.getMovieList()
    }

}