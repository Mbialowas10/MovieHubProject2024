package com.mbialowas.moviehubproject2024.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mbialowas.moviehubproject2024.api.model.Movie
import com.mbialowas.moviehubproject2024.api.model.MovieData
import com.mbialowas.moviehubproject2024.db.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesManager(database: AppDatabase) {
    private var _moviesResponse = mutableStateOf<List<Movie>>(emptyList()) // top level api call
    val api_key: String = "aaed4e12019db7b90c9cebd9c1082790"

    val moviesResponse: MutableState<List<Movie>>
        @Composable get() = remember {
            _moviesResponse
        }

    init {
        getMovies(database)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getMovies(database: AppDatabase) {
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

                    GlobalScope.launch {
                        saveDataToDatabase(database, _moviesResponse.value)
                    }
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("error", { t.message }.toString())
            }

        })
    }

    private suspend fun saveDataToDatabase(database: AppDatabase, movies: List<Movie>) {
        Log.i("RDB", "Save Data function called")
        database.movieDao().insertAllMovies(movies)
    }


}