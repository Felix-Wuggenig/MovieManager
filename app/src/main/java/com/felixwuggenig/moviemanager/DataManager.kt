package com.felixwuggenig.moviemanager

import android.content.res.Resources
import com.felixwuggenig.moviemanager.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader


class DataManager(val gson: Gson, val resources: Resources) {

    final val MOVIE_LIST_RESOURCE_ID = R.raw.movies
    final val STAFF_PICKS_FILE_NAME = R.raw.staff_picks


    fun getMovieList(): List<Movie> {
        val movieJsonString = readJsonFromRaw(MOVIE_LIST_RESOURCE_ID)
        val movieList: List<Movie> =
            gson.fromJson(movieJsonString, object : TypeToken<List<Movie>>() {})
        return movieList
    }

    fun getStaffPicks() {}

    fun readJsonFromRaw(resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
                reader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return stringBuilder.toString()
    }
}