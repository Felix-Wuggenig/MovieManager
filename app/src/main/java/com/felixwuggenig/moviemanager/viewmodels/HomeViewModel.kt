package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.data.DataManager
import com.felixwuggenig.moviemanager.data.SharedPreferences
import com.felixwuggenig.moviemanager.models.Movie

class HomeViewModel(
    val dataManager: DataManager,
    val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val mutableFavMovieData = MutableLiveData<List<Movie>>()
    val favMovieData: LiveData<List<Movie>> = mutableFavMovieData
    private val mutableStaffPickData = MutableLiveData<List<Movie>>()
    val staffPickData: LiveData<List<Movie>> = mutableStaffPickData
    private val mutableFavoritesData = MutableLiveData<List<Int>>()
    val favoritesData: LiveData<List<Int>> = mutableFavoritesData

    private val name = sharedPreferences.getName()

    fun loadFavMovieData() {
        val favIdList = sharedPreferences.getFavoriteIdList()
        mutableFavoritesData.value = favIdList
        mutableFavMovieData.value = dataManager.getMovieList().filter { favIdList.contains(it.id) }
    }

    fun updateFavMovies(id: Int) {
        if (mutableFavoritesData.value?.contains(id) == true) {
            sharedPreferences.removeFromFavList(id)
        } else {
            sharedPreferences.addToFavList(id)
        }
        loadFavMovieData()
    }

    fun loadStaffPickData() {
        mutableStaffPickData.value = dataManager.getStaffPicks()
    }

}