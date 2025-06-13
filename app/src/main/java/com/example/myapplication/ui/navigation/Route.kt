package com.example.myapplication.ui.navigation

import com.example.myapplication.utils.K

sealed class Route {
    data class HomeScreen(val route: String = "homeScreen") : Route()


    data class DiscoverMoviesScreen(
        val route: String = "DiscoverMoviesScreen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}"
    ) : Route()
    data class TrendingMoviesScreen(
        val route: String = "TrendingScreen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}"
    ) : Route()
    data class FilmScreen(
        val route: String = "filmScreen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}",

        ) : Route() {
        fun getRouteWithArgs(id: Int): String = "$route/$id"
    }
}

