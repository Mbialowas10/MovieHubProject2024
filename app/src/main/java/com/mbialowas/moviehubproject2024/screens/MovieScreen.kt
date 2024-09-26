package com.mbialowas.moviehubproject2024.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mbialowas.moviehubproject2024.api.MoviesManager
import com.mbialowas.moviehubproject2024.api.model.Movie



@Composable
fun MovieScreen(modifier: Modifier = Modifier, moviesManager: MoviesManager){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Blue)
    ){
        Text(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.Center),
            text = "Movie Screen"
        )
        val movies = moviesManager.movisResponse.value

        LazyColumn{
            items(movies){ movie ->
                MovieCard()

            }
        }
    }

}
@Composable
fun MovieCard(
    movie: Movie,
    navController: NavController
){
    Column(
        modifier = Modifier
            .border(1.dp, Color.Red, shape = RoundedCornerShape(10.dp))
            .padding(5.dp)
    ){
        Row(
            modifier = Modifier
                .background(color = Color.DarkGray)
                .fillMaxWidth()
                .padding(5.dp)
        ){
            AsyncImage(){

            }
        }
    }
}