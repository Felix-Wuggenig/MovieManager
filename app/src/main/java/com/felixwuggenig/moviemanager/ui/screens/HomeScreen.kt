package com.felixwuggenig.moviemanager.ui.screens

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
    val favoritesAdapter = remember { FavoriteMovieAdapter(emptyList()) }
    val movieAdapter = remember { MovieAdapter(emptyList(), onFavoriteClicked, emptyList()) }

    // Observe LiveData list and update adapter's data
    val movies by viewModel.staffPickData.observeAsState(emptyList())
    val favoriteIDs by viewModel.favoritesIdData.observeAsState(emptyList())
    val favorites by viewModel.favMovieData.observeAsState(emptyList())

    movieAdapter.updateMovies(movies, favoriteIDs)
    favoritesAdapter.updateFavoriteMovies(favorites)

    Column() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mainView =
                    LayoutInflater.from(context).inflate(R.layout.home_screen_layout, null)
                val username = mainView.findViewById<TextView>(R.id.username)
                username.text = viewModel.getName()
                val staffPicksRecyclerView =
                    mainView.findViewById<RecyclerView>(R.id.staffPicksList)
                staffPicksRecyclerView.layoutManager = LinearLayoutManager(context)
                staffPicksRecyclerView.adapter = movieAdapter
                val favoritesRecyclerView =
                    mainView.findViewById<RecyclerView>(R.id.favoritesRecyclerView)
                favoritesRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                favoritesRecyclerView.adapter = favoritesAdapter

                val searchIcon = mainView.findViewById<ImageButton>(R.id.searchIcon)
                searchIcon.setOnClickListener {
                    navController.navigate("search")
                }
                mainView
            }, update = {}
        )
    }
}