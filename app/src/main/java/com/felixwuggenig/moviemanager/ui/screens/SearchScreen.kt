package com.felixwuggenig.moviemanager.ui.screens

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
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
    val onItemClicked: (Int) -> Unit = { id ->
        navController.navigate("details/$id")
    }
    val movieAdapter =
        remember { MovieAdapter(emptyList(), emptyList(), onFavoriteClicked, onItemClicked) }

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

            val searchBar = mainView.findViewById<EditText>(R.id.searchViewMovie)
            searchBar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }

                override fun afterTextChanged(s: Editable?) {
                    val searchQuery = s.toString()
                    viewModel.updateSearchString(searchQuery)
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