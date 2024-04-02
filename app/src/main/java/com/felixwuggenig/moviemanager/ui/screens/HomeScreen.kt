package com.felixwuggenig.moviemanager.ui.screens

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.ui.adapter.FavoriteMovieAdapter
import com.felixwuggenig.moviemanager.ui.adapter.MovieAdapter
import com.felixwuggenig.moviemanager.viewmodels.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = getViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadFavMovieData()
        viewModel.loadStaffPickData()
    }
    val onFavoriteClicked: (Int) -> Unit = { id ->
        viewModel.updateFavMovies(id)
    }
    val onItemClicked: (Int) -> Unit = { id ->
        navController.navigate("details/$id")
    }

    val favoritesAdapter = remember { FavoriteMovieAdapter(emptyList(), onItemClicked) }
    val movieAdapter =
        remember {
            MovieAdapter(
                movies = emptyList(),
                favoritesIDs = emptyList(),
                onFavoriteClicked = onFavoriteClicked,
                onItemClicked = onItemClicked
            )
        }

    val movies by viewModel.staffPickData.observeAsState(emptyList())
    val favoriteIDs by viewModel.favoritesIdData.observeAsState(emptyList())
    val favorites by viewModel.favMovieData.observeAsState(emptyList())

    movieAdapter.updateMovies(newMovies = movies, newFavoritesIDs = favoriteIDs)
    favoritesAdapter.updateFavoriteMovies(newMovies = favorites)

    Column() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mainView =
                    LayoutInflater.from(context).inflate(R.layout.home_screen_layout, null)

                val username = mainView.findViewById<TextView>(R.id.username)
                username.text = viewModel.getName().ifEmpty { "User" }

                val staffPicksRecyclerView =
                    mainView.findViewById<RecyclerView>(R.id.staffPicksList)
                staffPicksRecyclerView.layoutManager = LinearLayoutManager(context)
                staffPicksRecyclerView.adapter = movieAdapter

                val favoritesRecyclerView =
                    mainView.findViewById<RecyclerView>(R.id.favoritesRecyclerView)
                favoritesRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                favoritesRecyclerView.adapter = favoritesAdapter

                val textViewFavorites = mainView.findViewById<TextView>(R.id.textViewFavoritesTitle)
                val spannableString1 = SpannableString("YOUR FAVORITES")

                spannableString1.setSpan(
                    StyleSpan(Typeface.BOLD),
                    5,
                    spannableString1.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                textViewFavorites.text = spannableString1

                val textViewStaffPicks =
                    mainView.findViewById<TextView>(R.id.textViewStaffPicksTitle)
                val spannableString2 = SpannableString("OUR STAFFPICKS")

                spannableString2.setSpan(
                    StyleSpan(Typeface.BOLD),
                    4,
                    spannableString2.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                textViewStaffPicks.text = spannableString2

                val searchIcon = mainView.findViewById<ImageButton>(R.id.searchIcon)
                searchIcon.setOnClickListener {
                    navController.navigate("search")
                }

                mainView
            }, update = {}
        )
    }
}