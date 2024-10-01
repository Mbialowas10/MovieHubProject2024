package com.mbialowas.moviehubproject2024.destinations

/*
    Where the app should go!
    sealed a way to defined a limited set of subclasses

    Purpose: A single source of truth for route names
 */
sealed class Destination(val route: String)  {
    object Movie : Destination("movie")
    object Watch : Destination("watch")
    object Search : Destination("search")
    object MovieDetail : Destination("movieDetail/{movieId}")

}