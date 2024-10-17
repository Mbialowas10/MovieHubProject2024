package com.mbialowas.moviehubproject2024.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.mbialowas.moviehubproject2024.api.model.Movie

@Composable
fun WatchScreen(
    modifier: Modifier = Modifier
        .background(color = Color.Red),
    navController: NavController
) {
    var data by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var movie_avg_vote: Any? = null
    var movie_id: Any? =null
    var movie_overview: Any? =null
    var movie_popularity: Any? =null
    var movie_poster_path: Any? =null
    var movie_release_date: Any? =null
    var movie_title: Any? =null
    var movie_vote_count: Any? =null
    var isFavorite: Any? =null

    // reference the movies collection
    val collectionReference = FirebaseFirestore.getInstance().collection("movies")
    var movie = Movie()

    collectionReference
        .get()
        .addOnSuccessListener { documents ->
            val dataList = documents.map { documentSnaphot ->
                val dataMap = documentSnaphot.data
                Movie(
                    id = dataMap["movie_id"] as? Int,
                    overview = dataMap["movie_overview"] as? String,
                    voteAverage = dataMap["movie_avg_vote"] as? Double,
                    voteCount = dataMap["movie_vote_count"] as? Int,
                    popularity = dataMap["movie_popularity"] as? Double,
                    poster_path = dataMap["movie_poster_path"] as? String,
                    releaseDate = dataMap["movie_release_date"] as? String,
                    title = dataMap["movie_title"] as? String,
                    isFavorite = dataMap["isFavorite"] as? Boolean
                )
            }
            data = dataList
        }
        .addOnFailureListener { exception ->
            // Handle failure
            Log.e("Firestore", "Error getting documents: ", exception)
        }
    Column{
        Text(text = "Watch Later Screen")
        LazyColumn {
            items(data){
                MovieCard(movie = it, navController = navController)
            }
        }
    }
}