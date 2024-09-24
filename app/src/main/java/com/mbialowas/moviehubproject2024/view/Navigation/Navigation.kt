package com.mbialowas.moviehubproject2024.view.Navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mbialowas.moviehubproject2024.R


@Composable
fun BottomNav(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val ic_movie = painterResource(id = R.drawable.ic_movie)
        val ic_search = painterResource(id = R.drawable.ic_search)
        val ic_watch = painterResource(id = R.drawable.ic_watch)

    }
}
