package com.felixwuggenig.moviemanager.data

import android.content.res.Resources
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.models.Movie
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader


class DataManager(val gson: Gson, val resources: Resources) {

    private val MOVIE_LIST_RESOURCE_ID = R.raw.movies
    private val STAFF_PICKS_FILE_NAME = R.raw.staff_picks

    fun getMovieList(): List<Movie> {
        val movieJsonString = readJsonFromRaw(MOVIE_LIST_RESOURCE_ID)
        return try {
            gson.fromJson(movieJsonString, object : TypeToken<List<Movie>>() {})
        } catch (e: JsonSyntaxException) {
            Timber.e(e)
            emptyList()
        } catch (e: JsonParseException) {
            Timber.e(e)
            emptyList()
        }
    }

    fun getStaffPicks(): List<Movie> {
        val staffPickJsonString = readJsonFromRaw(STAFF_PICKS_FILE_NAME)
        return try {
            gson.fromJson(staffPickJsonString, object : TypeToken<List<Movie>>() {})
        } catch (e: JsonSyntaxException) {
            Timber.e(e)
            emptyList()
        } catch (e: JsonParseException) {
            Timber.e(e)
            emptyList()
        }
    }

    private fun readJsonFromRaw(resourceId: Int): String {
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