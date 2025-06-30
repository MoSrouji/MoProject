package com.example.myapplication.route

import com.example.myapplication.utils.K

sealed class AppScreen() {

    data class SignInScreen(val route: String = ConstantAppScreen.SIGN_IN_SCREEN.name) : AppScreen()
    data class SignUpScreen(val route: String = ConstantAppScreen.SIGN_UP_SCREEN.name) : AppScreen()
    data class HomeScreen(val route: String = ConstantAppScreen.HOME_SCREEN.name) : AppScreen()
    data class SearchScreen(val route: String = ConstantAppScreen.SEARCH_SCREEN.name) : AppScreen()
    data class DiscoverMovieScreen(val route: String = ConstantAppScreen.DISCOVER_MOVIE_SCREEN.name) :
        AppScreen()

    data class TrendingMovieScreen(val route: String = ConstantAppScreen.TRENDING_MOVIE_SCREEN.name) :
        AppScreen()

    data class MovieDetailScreen(
        val route: String = ConstantAppScreen.MOVIE_DETAIL_SCREEN.name,
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}"
    ) :
        AppScreen() {
        fun getRouteWithArgs(id: Int): String = "$route/$id"
    }

}
