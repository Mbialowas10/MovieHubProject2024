package com.mbialowas.moviehubproject2024.mvvm

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.mbialowas.moviehubproject2024.api.Api
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.api.model.MovieData
import com.mbialowas.moviehubproject2024.db.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    var api_key:String = "aaed4e12019db7b90c9cebd9c1082790"

    val movies = mutableStateOf<List<Movie>>(emptyList())

    // search term
    val searchTerm = mutableStateOf("")

    // icon state
    var movieIconState = mutableStateOf<Map<Int,Boolean>> (emptyMap())

    val moviesResponse: MutableState<List<Movie>>
        @Composable get() = remember {
            movies
        }

    fun updateMovieIconState(movieId: Int, newState: Boolean, database: AppDatabase){
        GlobalScope.launch {
            database.movieDao().getMovieById(movieId).let{ movie ->
                movie?.isFavorite = !movie?.isFavorite!!
                database.movieDao().updateMovie(movie)

                movieIconState.value = movieIconState.value.toMutableMap().apply{
                    this[movieId] = movie.isFavorite!!
                }
            }
        }
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

}