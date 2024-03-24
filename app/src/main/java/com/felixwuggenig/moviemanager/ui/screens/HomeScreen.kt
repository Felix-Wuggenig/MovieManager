package com.felixwuggenig.moviemanager.ui.screens

import android.view.LayoutInflater
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.ui.adapter.MovieAdapter
import com.felixwuggenig.moviemanager.viewmodels.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = getViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadMovieData()
        viewModel.loadStaffPickData()
    }
    val onFavoriteClicked: (Int) -> Unit = { id ->
        //
    }
    val adapter = remember { MovieAdapter(emptyList(), onFavoriteClicked) }

    // Observe LiveData list and update adapter's data
    val movies by viewModel.staffPickData.observeAsState(emptyList())
    adapter.updateMovies(movies)

    Column() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mainView =
                    LayoutInflater.from(context).inflate(R.layout.home_screen_layout, null)
                val username = mainView.findViewById<TextView>(R.id.username)
                username.text = "Feelix"
                val staffPicksRecyclerView =
                    mainView.findViewById<RecyclerView>(R.id.staffPicksList)
                staffPicksRecyclerView.layoutManager = LinearLayoutManager(context)
                staffPicksRecyclerView.adapter = adapter

                mainView
            }, update = {}
        )
    }
}