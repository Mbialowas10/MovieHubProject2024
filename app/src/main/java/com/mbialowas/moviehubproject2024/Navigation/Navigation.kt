package com.mbialowas.moviehubproject2024.Navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mbialowas.moviehubproject2024.R
import com.mbialowas.moviehubproject2024.destinations.Destination

@Composable
fun BottomNav(navController: NavController){
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val ic_movie = painterResource(id = R.drawable.ic_movie)
        val ic_search = painterResource(id = R.drawable.ic_search)
        val ic_watch = painterResource(id = R.drawable.ic_watch)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Movie.route,
            onClick = { navController.navigate(Destination.Movie.route) {
                // two lines below act as stopping point in app,
                // so that app doesn't crash when you press back button
                popUpTo(Destination.Movie.route)
                launchSingleTop = true
            }},
            icon = { Icon(painter = ic_movie, contentDescription = "Movie Screen icon") },
            label = {
                Text(text = Destination.Movie.route)
            }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Search.route,
            onClick = { navController.navigate(Destination.Search.route) {
                // two lines below act as stopping point in app,
                // so that app doesn't crash when you press back button
                popUpTo(Destination.Search.route)
                launchSingleTop = true
            }},
            icon = { Icon(painter = ic_search, contentDescription = "Search Screen icon") },
            label = {
                Text(text = Destination.Search.route)
            }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Watch.route,
            onClick = { navController.navigate(Destination.Watch.route) {
                // two lines below act as stopping point in app,
                // so that app doesn't crash when you press back button
                popUpTo(Destination.Watch.route)
                launchSingleTop = true
            }},
            icon = { Icon(painter = ic_watch, contentDescription = "Watch Later Screen icon") },
            label = {
                Text(text = Destination.Watch.route)
            }
        )


    }
}