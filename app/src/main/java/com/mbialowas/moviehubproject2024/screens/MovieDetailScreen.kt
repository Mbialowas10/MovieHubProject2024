package com.mbialowas.moviehubproject2024.screens

import android.util.Log
import androidx.compose.foundation.background


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mbialowas.moviehubproject2024.api.model.Movie

@Composable
fun MovieDetailScreen(
    movie: Movie
){
    movie.originalTitle?.let { Log.i("Movie", it)}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Magenta, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ){
                Column() {
                    movie.overview?.let { Log.i("Movie", movie.overview) }
                    Text(
                        text = "Movie Id: {$movie.title}",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    movie.poster_path?.let {
                        Text(
                            text =it
                        )
                    }
                }
            }

}