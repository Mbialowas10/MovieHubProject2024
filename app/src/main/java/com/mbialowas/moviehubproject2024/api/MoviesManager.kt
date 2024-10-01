package com.mbialowas.moviehubproject2024.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.api.model.MovieData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MoviesManager {
    private var _moviesResponse = mutableStateOf<List<Movie>>(emptyList()) // top level api call
    val api_key: String ="aaed4e12019db7b90c9cebd9c1082790"

    val movisResponse: MutableState<List<Movie>>
        @Composable get() = remember{
            _moviesResponse
        }
    init{
        getMovies()
    }
    private fun getMovies(){
        val service = Api.retrofitService.getTrendingMovies(api_key)

        service.enqueue(object : Callback<MovieData> {
            override fun onResponse(
                call: Call<MovieData>,
                response: Response<MovieData>
            ) {
                if (response.isSuccessful) {
                    Log.i("Data", "Data Loaded")
                    _moviesResponse.value = response.body()?.results ?: emptyList()
                    Log.i("DataStream", _moviesResponse.toString())
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("error", {t.message}.toString())
            }
        })
    }



}