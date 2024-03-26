package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.data.SharedPreferences
import com.felixwuggenig.moviemanager.models.Movie

class DetailsViewModel(
    val dataManager: DataManager,
    val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val movieData = MutableLiveData<Movie>()
    val mutableMovieData: LiveData<Movie> = movieData


    private val mutableFavoritesIdData = MutableLiveData<List<Int>>()
    val favoritesIdData: LiveData<List<Int>> = mutableFavoritesIdData

    fun loadMovieData(id: Int) {
        val favIdList = sharedPreferences.getFavoriteIdList()
        mutableFavoritesIdData.value = favIdList
        movieData.value = dataManager.getMovieList().first { it.id == id }
    }

    private fun updateFavoritesList() {
        val favIdList = sharedPreferences.getFavoriteIdList()
        mutableFavoritesIdData.value = favIdList
    }

    fun updateFavMovies(id: Int) {
        if (mutableFavoritesIdData.value?.contains(id) == true) {
            sharedPreferences.removeFromFavList(id)
        } else {
            sharedPreferences.addToFavList(id)
        }
        updateFavoritesList()
    }

}