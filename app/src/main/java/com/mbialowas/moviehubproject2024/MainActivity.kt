package com.mbialowas.moviehubproject2024

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubproject2024.Navigation.BottomNav
import com.mbialowas.moviehubproject2024.api.MoviesManager
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.db.AppDatabase
import com.mbialowas.moviehubproject2024.destinations.Destination
import com.mbialowas.moviehubproject2024.mvvm.MovieViewModel
import com.mbialowas.moviehubproject2024.screens.MovieDetailScreen
import com.mbialowas.moviehubproject2024.screens.MovieScreen
import com.mbialowas.moviehubproject2024.screens.SearchScreen
import com.mbialowas.moviehubproject2024.screens.WatchScreen
import com.mbialowas.moviehubproject2024.ui.theme.MovieHubProject2024Theme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubProject2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    //database
                    val db = AppDatabase.getInstance(applicationContext)
                    //fetch movie data from api
                    val moviesManager: MoviesManager = MoviesManager(db)

                    // initialize the viewmodel
                    val viewModel: MovieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

                    // code to draw the screen goes here
//                    MovieScreen(modifier = Modifier.padding(innerPadding))
                    App(navController = navController, modifier = Modifier.padding(innerPadding), moviesManager,db, viewModel)

                }
            }
        }
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController, modifier: Modifier = Modifier ,moviesManager: MoviesManager, db: AppDatabase, viewModel: MovieViewModel) {
    var movie by remember {
        mutableStateOf<Movie?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MovieHub Project Fall 2024") }
            )
        },
        content = { innerPadding ->
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )
            NavHost(navController = navController as NavHostController, startDestination = Destination.Movie.route) {
                composable(Destination.Movie.route) {
                    MovieScreen(modifier = modifier, moviesManager ,navController)
                }
                composable(Destination.Watch.route) {
                    WatchScreen()
                }
                composable(Destination.Search.route) {
                    SearchScreen(modifier = Modifier.padding(innerPadding), viewModel, db, navController)
                }
                composable(Destination.MovieDetail.route) { navBackStackEntry ->
                    val movie_id:String? = navBackStackEntry.arguments?.getString("movieID")
                    GlobalScope.launch{
                        if (movie_id != null){
                            movie = db.movieDao().getMovieById(movie_id.toInt())
                        }
                    }

                    movie?.let { MovieDetailScreen(it, modifier = Modifier.padding()) }
                }
            }
        },

        bottomBar = { BottomNav(navController = navController) }
    )
}






