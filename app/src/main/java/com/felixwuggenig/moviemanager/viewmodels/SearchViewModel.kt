package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.data.SharedPreferences
import com.felixwuggenig.moviemanager.models.Movie

class SearchViewModel(
    private val dataManager: DataManager,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val mutableMovieData = MutableLiveData<List<Movie>>()
    val movieData: LiveData<List<Movie>> = mutableMovieData

    private val mutableFavoritesIdData = MutableLiveData<List<Int>>()
    val favoritesIdData: LiveData<List<Int>> = mutableFavoritesIdData

    private var searchString: String = ""

    fun updateSearchString(newString: String) {
        searchString = newString
        loadMovieData()
    }

    fun loadMovieData() {
        val favIdList = sharedPreferences.getFavoriteIdList()
        mutableFavoritesIdData.value = favIdList
        mutableMovieData.value = dataManager.getMovieList()
            .filter { it.title.lowercase().contains(searchString.lowercase()) }
    }

    fun updateFavMovies(id: Int) {
        if (mutableFavoritesIdData.value?.contains(id) == true) {
            sharedPreferences.removeFromFavList(id)
        } else {
            sharedPreferences.addToFavList(id)
        }
        loadMovieData()
    }
}