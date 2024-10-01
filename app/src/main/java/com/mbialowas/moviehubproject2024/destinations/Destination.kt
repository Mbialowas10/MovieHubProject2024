package com.mbialowas.moviehubproject2024.destinations

/*
 This class is responsible for where the app should navigate to.
 by using sealed classes, we are not able to further subclass this class.
 */
sealed class Destination(val route: String) {
    object Movie: Destination("movie")
    object Search: Destination("search")
    object Watch:Destination("watch")

    object MovieDetail: Destination("movieDetail/{movieID}"){
        fun createRoute(movieID: Int?) = "movieDetail/$movieID"
    }

}