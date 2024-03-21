package com.felixwuggenig.moviemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.felixwuggenig.moviemanager.ui.DetailsScreen
import com.felixwuggenig.moviemanager.ui.HomeScreen
import com.felixwuggenig.moviemanager.ui.SearchScreen
import com.felixwuggenig.moviemanager.ui.SignUpScreen
import com.felixwuggenig.moviemanager.ui.theme.MovieManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}


@Composable
fun MainView() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "signUp") {
        composable("signUp") {
            SignUpScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("details") {
            DetailsScreen(navController = navController)
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
    }
}