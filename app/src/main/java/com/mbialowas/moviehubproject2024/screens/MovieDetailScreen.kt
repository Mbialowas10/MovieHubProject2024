package com.mbialowas.moviehubproject2024.screens

import android.util.Log
import androidx.compose.foundation.background


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mbialowas.moviehubproject2024.api.model.Movie

@Composable
fun MovieDetailScreen(
    movie: Movie,
    modifier: Modifier
){
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
                        AsyncImage(
                            model =  ImageRequest.Builder(
                                LocalContext.current
                            ).data("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                                .build(),
                            contentDescription = movie.overview,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    movie.poster_path?.let {
                        Text(
                            text =it
                        )
                    }
                }
            }

}