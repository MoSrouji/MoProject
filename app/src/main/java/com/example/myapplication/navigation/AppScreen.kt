package com.example.myapplication.navigation

import com.example.myapplication.utils.K

sealed class AppScreen() {

    data class SignInScreen(val route: String = ConstantAppScreen.SIGN_IN_SCREEN.name) : AppScreen()
    data class SignUpScreen(val route: String = ConstantAppScreen.SIGN_UP_SCREEN.name) : AppScreen()
    data class HomeScreen(val route: String = ConstantAppScreen.HOME_SCREEN.name) : AppScreen()
    data class SearchScreen(val route: String = ConstantAppScreen.SEARCH_SCREEN.name) : AppScreen()
    data class UserDetailScreen(val route: String = ConstantAppScreen.USER_DETAIL_SCREEN.name) : AppScreen()
    data class UpdateUserDetailScreen(val route: String = ConstantAppScreen.UPDATE_USER_DETAIL_SCREEN.name) : AppScreen()
    data class SavedMoviesScreen(val route :String= ConstantAppScreen.SAVED_MOVIES.name): AppScreen()
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
