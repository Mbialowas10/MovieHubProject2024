package com.mbialowas.moviehubproject2024.mvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mbialowas.moviehubproject2024.api.Api
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.api.model.MovieData
import com.mbialowas.moviehubproject2024.db.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
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
}