package com.felixwuggenig.moviemanager.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SignUpScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.SignUpScreen(navController = navController)
}

@Composable
fun HomeScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.HomeScreen(navController = navController)
}

@Composable
fun DetailsScreen(navController: NavController, movieId: Int) {
    com.felixwuggenig.moviemanager.ui.screens.DetailsScreen(navController = navController,movieId=movieId)
}

@Composable
fun SearchScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.SearchScreen(navController = navController)
}
