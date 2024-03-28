package com.felixwuggenig.moviemanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.models.Director
import com.felixwuggenig.moviemanager.models.Movie
import com.felixwuggenig.moviemanager.ui.theme.DetailsScreenRoundTextBackground
import com.felixwuggenig.moviemanager.ui.theme.DetailsScreenRoundTextColor
import com.felixwuggenig.moviemanager.ui.theme.RatingStarColorActive
import com.felixwuggenig.moviemanager.viewmodels.DetailsViewModel
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import org.koin.androidx.compose.get
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale

@Composable
fun DetailsScreen(navController: NavController, movieId: Int) {

    val viewModel: DetailsViewModel = get()
    LaunchedEffect(Unit) {
        viewModel.loadMovieData(movieId)
    }
    val movie: Movie? = viewModel.mutableMovieData.observeAsState().value
    val favoritesIdList: List<Int> = viewModel.favoritesIdData.observeAsState(emptyList()).value

    if (movie != null) {
        LazyColumn(userScrollEnabled = true) {
            item {
                DetailsView(
                    movie = movie,
                    isFavorite = favoritesIdList.contains(movie.id),
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
    isFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    onCloseCLicked: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                IconButton(onClick = onFavoriteClicked) {
                    if (isFavorite) {
                        Image(
                            painter = painterResource(R.drawable.favorite_on),
                            contentDescription = "Unfavorite"
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.favorite_off),
                            contentDescription = "Favorite"
                        )
                    }
                }
                IconButton(onClick = onCloseCLicked) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
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
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                    RatingBar(
                        value = movie.rating.toFloat(),
                        config = RatingBarConfig()
                            .activeColor(RatingStarColorActive)
                            .size(20.dp)
                            .inactiveColor(DetailsScreenRoundTextBackground)
                            .isIndicator(true),
                        onRatingChanged = {},
                        onValueChange = {},
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "${movie.releaseDate} · ${movie.runtime.toInt() / 60}h ${movie.runtime % 60}m",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 8.dp,
                            start = 48.dp,
                            end = 48.dp
                        )
                    ) {
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(movie.title)
                            }
                            withStyle(style = SpanStyle(color = Color.DarkGray)) {
                                append(" (${movie.releaseDate.year})")
                            }
                        }
                        Text(
                            text = annotatedString,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )

                    }
                    Row {
                        movie.genres.forEach { genre ->
                            RoundedText(
                                text = genre,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            }

            // Overview Section
            Text(
                modifier = Modifier.padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 32.dp,
                    bottom = 16.dp
                ),
                text = "Overview",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = movie.overview,
                style = MaterialTheme.typography.bodyLarge.copy(color = DetailsScreenRoundTextColor)
            )

            // Key Facts Section
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 32.dp),
                text = "Key Facts",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 32.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                val ratingString = String.format(Locale.getDefault(), "%.2f", movie.rating)
                FactsView(
                    budget = movie.budget,
                    revenue = movie.revenue,
                    ogLanguage = movie.language,
                    rating = ratingString,
                    ratingNumbers = movie.reviews.toString()
                )
            }
        }
    }
}

@Composable
fun RoundedText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Box(
        modifier = modifier
            .background(color = DetailsScreenRoundTextBackground, shape = RoundedCornerShape(11.dp))
    ) {
        Text(
            text = text,
            style = textStyle,
            color = Color.Black,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun FactsView(
    budget: Long,
    revenue: Long,
    ogLanguage: String,
    rating: String,
    ratingNumbers: String,
    modifier: Modifier = Modifier
) {
    val formatter =
        NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat
    formatter.applyPattern("#,###")

    LazyHorizontalGrid(rows = GridCells.Fixed(2), modifier = modifier.fillMaxWidth()) {
        item {
            val formattedBudget = formatter.format(budget)
            Fact(title = "Budget", info = "$ $formattedBudget")
        }
        item {
            Fact(
                title = "Original language",
                info = Locale(ogLanguage).displayLanguage ?: ogLanguage
            )
        }
        item {
            val formattedRev = formatter.format(revenue)
            Fact(title = "Revenue", info = "$ $formattedRev")
        }
        item {
            Fact(title = "Rating", info = "$rating ($ratingNumbers)")
        }
    }
}

@Composable
fun Fact(title: String, info: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(containerColor = DetailsScreenRoundTextBackground),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
                .height(80.dp)
                .width(150.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = info,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsViewPreview() {
    DetailsView(
        movie = Movie(
            3.toDouble(),
            1,
            1000L,
            LocalDate.now(),
            Director("Dir ector", ""),
            "",
            listOf(),
            120L,
            "Movie title",
            "overview text",
            4L,
            100L,
            "en",
            emptyList()
        ),
        isFavorite = true,
        onFavoriteClicked = {},
        onCloseCLicked = {}
    )
}