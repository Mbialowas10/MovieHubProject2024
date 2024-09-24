package com.mbialowas.moviehubproject2024.destinations

/*
    Where the app should go!
    sealed a way to defined a limited set of subclasses
 */
sealed class Destination(val route: String)  {
    object MovieScreen : Destination("movie")
    object WatchScreen : Destination("watch")
    object SearchScreen : Destination("search")

}