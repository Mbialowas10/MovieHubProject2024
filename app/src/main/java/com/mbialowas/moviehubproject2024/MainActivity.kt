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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubproject2024.api.MoviesManager
import com.mbialowas.moviehubproject2024.destinations.Destination
import com.mbialowas.moviehubproject2024.screens.FavoriteScreen


import com.mbialowas.moviehubproject2024.screens.MovieScreen
import com.mbialowas.moviehubproject2024.screens.SearchScreen
import com.mbialowas.moviehubproject2024.ui.theme.MovieHubProject2024Theme
import com.mbialowas.moviehubproject2024.view.Navigation.BottomNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubProject2024Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()

                ) { innerPadding ->
                    val navController = rememberNavController()

                    // fetch data
                    val moviesManager = MoviesManager()

                    //MovieScreen(modifier = Modifier.padding(innerPadding))
                    App(navController = navController, modifier = Modifier.padding(innerPadding), moviesManager)


                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier, moviesManager: MoviesManager){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MovieHub Fall 2024") }
            )
        },
        bottomBar = { BottomNav(navController = navController) }

    ){ paddingValues ->
        paddingValues.calculateBottomPadding()
        Spacer(modifier = Modifier.padding(10.dp))

        NavHost(navController = navController as NavHostController, startDestination = Destination.Movie.route){
            composable(Destination.Movie.route){
                MovieScreen(modifier = Modifier.padding(paddingValues), moviesManager)
            }
            composable(Destination.Watch.route){
                FavoriteScreen(modifier = Modifier.padding(paddingValues))
            }
            composable(Destination.Search.route){
                SearchScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}


