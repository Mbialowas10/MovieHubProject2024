package com.mbialowas.moviehubproject2024.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mbialowas.moviehubproject2024.db.AppDatabase
import com.mbialowas.moviehubproject2024.mvvm.MovieViewModel

@Composable
fun SearchScreen(modifier: Modifier, viewModel: MovieViewModel, database: AppDatabase,navController: NavController){
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var query by rememberSaveable { viewModel.searchTerm } // come back to this and viewmodel


    Box(modifier = Modifier
        .background(color = Color.LightGray)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth(),
                text="Search Screen",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = viewModel.searchTerm.value,
                onValueChange = {
                    viewModel.searchTerm.value = it
                },
                label = {Text("Search for a movie.")},
                keyboardOptions =  KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(color = Color.White)
            )
            Button(onClick = {
                query = ""
                viewModel.searchMovies(query, database)
                keyboardController?.hide()
                Log.d("SearchScreen", query )
            }){
                Text("Search")
            }
        }

    }
    Spacer(
        modifier = Modifier
            .height(10.dp)
            .fillMaxWidth()
    )
    for(movie in viewModel.movies.value){
        LazyColumn {
            items(viewModel.movies.value){
                MovieCard(movie, navController = navController)
            }
        }
    }
}