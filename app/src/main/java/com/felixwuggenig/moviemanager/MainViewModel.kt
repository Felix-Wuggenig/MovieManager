package com.felixwuggenig.moviemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.models.Movie

class MainViewModel(
    val dataManager: DataManager,
) : ViewModel() {
    private val _movieData = MutableLiveData<List<Movie>>()
    val movieData: LiveData<List<Movie>> = _movieData

    // Function to update the data
    fun loadMovieData() {
        _movieData.value = dataManager.getMovieList()
    }

}