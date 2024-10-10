package com.mbialowas.moviehubproject2024.mvvm

import android.util.Log
<<<<<<< HEAD
import androidx.compose.runtime.mutableStateOf
=======
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
>>>>>>> 07_MVVM_s279
import androidx.lifecycle.ViewModel
import com.mbialowas.moviehubproject2024.api.Api
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.api.model.MovieData
import com.mbialowas.moviehubproject2024.db.AppDatabase
<<<<<<< HEAD
=======
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
>>>>>>> 07_MVVM_s279
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
<<<<<<< HEAD
    val api_key: String ="aaed4e12019db7b90c9cebd9c1082790"
    val movies = mutableStateOf<List<Movie>>(emptyList())

    var searchTerm = mutableStateOf("")

//    val moviesReponse:

    fun searchMovies(movieName:String, database: AppDatabase){

        if (movieName.isNotBlank()){
            //api call
            val service = Api.retrofitService.searchMovieByName(api_key, movieName)
            service.enqueue(object : Callback<MovieData>{
                override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                    if (response.isSuccessful){
                        Log.i("SearchData", "testing testing")

                    }
                }

                override fun onFailure(call: Call<MovieData>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        }
    }
=======

    var api_key:String = "aaed4e12019db7b90c9cebd9c1082790"

    val movies = mutableStateOf<List<Movie>>(emptyList())

    // search term
    val searchTerm = mutableStateOf("")

    val moviesResponse: MutableState<List<Movie>>
        @Composable get() = remember {
            movies
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchMovies(movieName:String, database: AppDatabase){
        // api call
        val service = Api.retrofitService.searchMovieByName(api_key,movieName)
        service.enqueue(object : Callback<MovieData>{
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                if (response.isSuccessful){
                    Log.i("SearchData", "testing testing")
                    movies.value = response.body()?.results ?: emptyList()
                    Log.i("MovieFound", movies.toString())
                    GlobalScope.launch {
                        database.movieDao().insertAllMovies(movies=movies.value)
                    }
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("error", "${t.message}")
            }

        })

    }
    fun saveSearchTerm(term:String){
        searchTerm.value = term

    }

>>>>>>> 07_MVVM_s279
}