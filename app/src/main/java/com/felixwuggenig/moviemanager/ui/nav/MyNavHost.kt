package com.felixwuggenig.moviemanager.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.felixwuggenig.moviemanager.ui.DetailsScreen
import com.felixwuggenig.moviemanager.ui.HomeScreen
import com.felixwuggenig.moviemanager.ui.SearchScreen
import com.felixwuggenig.moviemanager.ui.SignUpScreen

@Composable
fun MyNavHost(navController:NavHostController) {
    NavHost(navController, startDestination = "signUp") {
        composable("signUp") {
            SignUpScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable(
            "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt("movieId")
            DetailsScreen(navController = navController, movieId = movieId ?: 0)
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
    }
}