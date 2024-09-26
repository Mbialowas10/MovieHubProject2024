package com.mbialowas.moviehubproject2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubproject2024.Navigation.BottomNav
import com.mbialowas.moviehubproject2024.api.MoviesManager
import com.mbialowas.moviehubproject2024.destinations.Destination
import com.mbialowas.moviehubproject2024.screens.MovieScreen
import com.mbialowas.moviehubproject2024.screens.SearchScreen
import com.mbialowas.moviehubproject2024.screens.WatchScreen
import com.mbialowas.moviehubproject2024.ui.theme.MovieHubProject2024Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubProject2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    //fetch movie data from api
                    val moviesManager: MoviesManager = MoviesManager

                    // code to draw the screen goes here
//                    MovieScreen(modifier = Modifier.padding(innerPadding))
                    App(navController = navController, modifier = Modifier.padding(innerPadding), moviesManager)

                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController, modifier: Modifier = Modifier ,moviesManager: MoviesManager) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MovieHub Project Fall 2024") }
            )
        }, bottomBar = { BottomNav(navController = navController) }
    ) { paddingValues ->
        paddingValues.calculateBottomPadding()
        Spacer(modifier = Modifier.padding(10.dp))
        NavHost(navController = navController as NavHostController, startDestination = Destination.Movie.route) {
            composable(Destination.Movie.route) {
                MovieScreen(modifier = modifier, moviesManager)
            }
            composable(Destination.Watch.route) {
                WatchScreen()
            }
            composable(Destination.Search.route) {
                SearchScreen()
            }
        }
    }
}






