package com.felixwuggenig.moviemanager.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class MovieList(
    val movies: List<Movie>
)

data class Movie(
    val rating: Double,
    val id: Int,
    val revenue: Long,
    val releaseDate: LocalDate,
    val director: Director,
    @SerializedName("posterUrl")
    val posterURL: String,
    val cast: List<Cast>,
    val runtime: Long,
    val title: String,
    val overview: String,
    val reviews: Long,
    val budget: Long,
    val language: String,
    val genres: List<String>
)

data class Cast(
    val name: String,
    @SerializedName("pictureUrl")
    val pictureURL: String,
    val character: String
)

data class Director(
    val name: String,
    @SerializedName("pictureUrl")
    val pictureURL: String
)