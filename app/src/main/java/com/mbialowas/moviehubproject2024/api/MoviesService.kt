package com.mbialowas.moviehubproject2024.api

import com.mbialowas.moviehubproject2024.api.model.MovieData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService{
    @GET("trending/movie/day")
    fun getTrendingMovies(@Query("api_key") apiKey: String): Call<MovieData> //calling the parent
}