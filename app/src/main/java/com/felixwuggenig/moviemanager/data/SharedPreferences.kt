package com.felixwuggenig.moviemanager.data

// Preferences.kt

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferences(val context: Context, val gson: Gson) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    private val KEY_NAME = "name"
    private val KEY_FAV_LIST = "fav_list"

    private val DEFAULT_NAME = "John Doe"

    fun saveName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, name)
        editor.apply()
    }

    fun getName(): String {
        return sharedPreferences.getString(KEY_NAME, "") ?: ""
    }


    fun addToFavList(id: Int) {
        val editor = sharedPreferences.edit()
        val json = sharedPreferences.getString(KEY_FAV_LIST, null)
        val list: MutableList<Int> = if (json != null) {
            gson.fromJson(json, object : TypeToken<MutableList<Int>>() {}.type)
        } else {
            mutableListOf()
        }
        if (!list.contains(id)) {
            list.add(id)
        }
        val newJson = gson.toJson(list)
        editor.putString(KEY_FAV_LIST, newJson)
        editor.apply()
    }

    fun removeFromFavList(id: Int) {
        val editor = sharedPreferences.edit()
        val json = sharedPreferences.getString(KEY_FAV_LIST, null)
        val list: MutableList<Int> = if (json != null) {
            gson.fromJson(json, object : TypeToken<MutableList<Int>>() {}.type)
        } else {
            mutableListOf()
        }
        if (list.contains(id)) {
            list.remove(id)
        }
        val newJson = gson.toJson(list)
        editor.putString(KEY_FAV_LIST, newJson)
        editor.apply()
    }

    fun getFavoriteIdList(): List<Int> {
        val json = sharedPreferences.getString(KEY_FAV_LIST, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Int>>() {}.type)
        } else {
            emptyList()
        }
    }
}