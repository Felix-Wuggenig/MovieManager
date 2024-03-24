package com.felixwuggenig.moviemanager.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SignUpScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.SignUpScreen(navController = navController)
}

@Composable
fun HomeScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.HomeScreen()
}

@Composable
fun DetailsScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.DetailsScreen()
}

@Composable
fun SearchScreen(navController: NavController) {
    com.felixwuggenig.moviemanager.ui.screens.SearchScreen()
}
