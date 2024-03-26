package com.felixwuggenig.moviemanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.felixwuggenig.moviemanager.models.Movie
import com.felixwuggenig.moviemanager.viewmodels.DetailsViewModel
import org.koin.androidx.compose.get
import java.util.Locale

@Composable
fun DetailsScreen(navController: NavController, movieId: Int) {

    val viewModel: DetailsViewModel = get()
    LaunchedEffect(Unit) {
        viewModel.loadMovieData(movieId)
    }
    val movie: Movie? = viewModel.mutableMovieData.observeAsState().value

    if (movie != null) {
        LazyColumn(userScrollEnabled = true) {
            item {
                DetailsView(
                    movie = movie,
                    onFavoriteClicked = {
                        viewModel.updateFavMovies(movieId)
                    },
                    onCloseCLicked = {
                        navController.navigateUp()
                    })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsView(
    movie: Movie,
    onFavoriteClicked: () -> Unit,
    onCloseCLicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top Right Corner Icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                IconButton(onClick = onFavoriteClicked) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                }
                IconButton(onClick = onCloseCLicked) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            // Content Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    GlideImage(
                        model = movie.posterURL,
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                    )
                    Text(text = movie.title, style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = movie.releaseDate.year.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                    movie.genres.forEach { genre ->
                        RoundedText(
                            text = genre,
                        )
                    }
                }
            }

            // Overview Section
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Overview",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall
            )

            // Key Facts Section
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                text = "Key Facts",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val ratingString = String.format(Locale.getDefault(), "%.2f", movie.rating)
                FactsView(
                    budget = movie.budget.toString(),
                    revenue = movie.revenue.toString(),
                    ogLanguage = movie.language,
                    rating = ratingString,
                    ratingNumbers = movie.reviews.toString()
                )
            }
        }
    }
}

@Composable
fun RoundedText(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color.Blue, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun FactsView(
    budget: String,
    revenue: String,
    ogLanguage: String,
    rating: String,
    ratingNumbers: String
) {
    Row() {
        Column() {
            Fact(title = "Budget", budget)
            Fact(title = "Revenue", revenue)
        }
        Column() {
            Fact(title = "Original language", ogLanguage)
            Fact(title = "Rating", "$rating ($ratingNumbers)")
        }
    }
}

@Composable
fun Fact(title: String, info: String) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = info,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}