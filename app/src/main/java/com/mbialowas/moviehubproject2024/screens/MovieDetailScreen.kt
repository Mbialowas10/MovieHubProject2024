package com.mbialowas.moviehubproject2024.screens

import android.util.Log
import androidx.compose.foundation.background


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.db.AppDatabase
import com.mbialowas.moviehubproject2024.mvvm.MovieViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun MovieDetailScreen(
    movie: Movie,
    modifier: Modifier,
    viewModel: MovieViewModel,
    database: AppDatabase,
    fs_db: FirebaseFirestore

){
    //
    val isIconChanged = viewModel.movieIconState.value[movie.id] ?: false;
    var lastInsertedDocument by remember { mutableStateOf<DocumentReference?>(null) }

    movie.originalTitle?.let { Log.i("Movie", it)}
    Box(
        modifier = Modifier
            .fillMaxSize() ,
            contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier
                        .background(
                            color = Color.DarkGray
                        )
                ) {
                    movie.overview?.let { Log.i("Movie", movie.overview) }
                    Text(
                        modifier = Modifier
                            .background(color = Color.Black)
                            .padding(1.dp, 5.dp, 1.dp, 1.dp)
                            .fillMaxWidth(),
                        text = "Movie Detail Screen",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center

                    )
                    Box{
                        AsyncImage(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                            model =  ImageRequest.Builder(
                                LocalContext.current
                            ).data("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                                .build(),
                            contentDescription = movie.overview,
                            contentScale = ContentScale.FillBounds
                        )


                        movie.poster_path?.let {
                            Text(
                                text =it
                            )
                        }
                        Button(
                            onClick = {
                                Log.i("Button", "Button Clicked")
                                var iconState = !isIconChanged // toggle the value when composble gets 1st emitted
                                var movieExists:Boolean? = null

                                viewModel.updateMovieIconState(movie.id!!, database = database)
                                Log.i("Favourite", iconState.toString())

                                val collection: CollectionReference = FirebaseFirestore.getInstance().collection("movies")
                                val m = hashMapOf(
                                    "movie_id"              to      "${movie.id}",
                                    "movie_title"           to      "${movie.title}",
                                    "movie_overview"        to      "${movie.overview}",
                                    "movie_poster_path"     to      "${movie.poster_path}",
                                    "movie_release_date"    to     "${movie.releaseDate}",
                                    "movie_popularity"      to     "${movie.popularity}",
                                    "movie_avg_vote"        to      "${movie.voteAverage}",
                                    "movie_vote_count"      to      "${movie.voteCount}",
                                    "isFavorite"   to     "${movie.isFavorite}"
                                )
                                // check if movie exists in firestore database
                                GlobalScope.launch {
                                    movieExists = doesMovieExist(movie.id.toString(), collection)
                                    if (iconState){
                                        if (movieExists == true){
                                            Log.d("FS", "Sorry but you can't insert the same movie. Please try again.")
                                        }else{
                                            fs_db.collection("movies").add(m)
                                                .addOnSuccessListener { documentReference ->
                                                    lastInsertedDocument = documentReference

                                                    Log.d("FS", "DocumentSnapshot added with ID: ${documentReference.id}")
                                                }
                                                .addOnFailureListener { e ->
                                                    Log.w("FS", "Error adding document", e)
                                                }
                                        }
                                    }else if(!iconState){
                                        lastInsertedDocument?.delete()?.addOnSuccessListener{
                                            Log.i("Removal", "${movie.title} was removed from Firestore Database")
                                        }
                                            ?.addOnFailureListener{
                                                Log.i("Removal", "THERE WAS A PROBLEM REMOVING THE RECORD FROM FS ${it.message}")
                                            }
                                    }

                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                        ){
                            Icon(
                               imageVector = if (isIconChanged == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Watch",
                                tint = Color.White
                            )
                        }
                    } // end box
                    Column(Modifier.padding(20.dp)){
                        Spacer(modifier = Modifier.padding(5.dp))
                        movie.releaseDate?.let {
                            Text(
                                text= "Release Date: $it",
                                modifier = Modifier.padding(end=5.dp),
                                maxLines = 9,
                                overflow= TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelLarge,
                                color=Color.White
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            movie.overview?.let {
                                Text(
                                    text= it,
                                    modifier = Modifier.padding(end=8.dp),
                                    maxLines = 5,
                                    overflow= TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color=Color.White
                                )
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                            Row{
                              movie.voteAverage?.let{
                                  Text(
                                      text= "Avg Vote: $it",
                                      modifier = Modifier.padding(end=8.dp),
                                      maxLines = 9,
                                      overflow= TextOverflow.Ellipsis,
                                      style = MaterialTheme.typography.labelLarge,
                                      color=Color.White
                                  )
                              }
                               movie.voteCount?.let{
                                   Text(
                                       text= "# of votes: $it",
                                       modifier = Modifier.padding(end=8.dp),
                                       maxLines = 9,
                                       overflow= TextOverflow.Ellipsis,
                                       style = MaterialTheme.typography.labelLarge,
                                       color=Color.White
                                   )
                               }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

}

// a check to see which movies already exist in firstore db
suspend fun doesMovieExist(movie_id: String, collection: CollectionReference): Boolean{
    val querySnapshot = collection.whereEqualTo("movie_id", movie_id).get().await()
    return !querySnapshot.isEmpty

}