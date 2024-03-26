package com.felixwuggenig.moviemanager.ui.screens

import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
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
import com.felixwuggenig.moviemanager.ui.adapter.MovieAdapter
import com.felixwuggenig.moviemanager.viewmodels.SearchViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchViewModel = getViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadMovieData()
    }
    val onFavoriteClicked: (Int) -> Unit = { id ->
        viewModel.updateFavMovies(id)
    }
    val movieAdapter = remember { MovieAdapter(emptyList(), onFavoriteClicked, emptyList()) }

    // Observe LiveData list and update adapter's data
    val movies by viewModel.movieData.observeAsState(emptyList())
    val favoriteIDs by viewModel.favoritesIdData.observeAsState(emptyList())
    movieAdapter.updateMovies(movies, favoriteIDs)


    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mainView =
                LayoutInflater.from(context).inflate(R.layout.search_screen_layout, null)
            val searchResultList = mainView.findViewById<RecyclerView>(R.id.searchResultsList)
            searchResultList.layoutManager = LinearLayoutManager(context)
            searchResultList.adapter = movieAdapter
            val searchBar = mainView.findViewById<SearchView>(R.id.searchViewMovie)
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.updateSearchString(query)
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    //do nothing, searching is done in onQueryTextChange
                    return false
                }
            })
            val backButton = mainView.findViewById<ImageButton>(R.id.backButton)
            backButton.setOnClickListener {
                navController.navigateUp()
            }
            mainView
        }
    )
}