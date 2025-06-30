package com.example.myapplication.route

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing

import androidx.compose.animation.core.tween

import androidx.compose.animation.slideInHorizontally

import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.TopAppBar
import com.example.myapplication.ui.auth.presentaiton.authentication.signin.SignInScreen
import com.example.myapplication.ui.auth.presentaiton.authentication.signup.SignUpScreen
import com.example.myapplication.ui.detail.MovieDetailScreen
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.moviesScreenDiscoverAndTrending.DiscoverMoviesScreen
import com.example.myapplication.ui.moviesScreenDiscoverAndTrending.TrendingMoviesScreen
import com.example.myapplication.ui.search.SearchScreen
import com.example.myapplication.utils.K

object NavAnimations {
    // Default animation specs
    private const val DEFAULT_DURATION = 300
    private val DEFAULT_EASING = FastOutSlowInEasing

    // Slide animations
    fun slideInFromRight() = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(DEFAULT_DURATION, easing = DEFAULT_EASING)
    )

    fun slideOutToLeft() = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(DEFAULT_DURATION, easing = DEFAULT_EASING)
    )

}

// Usage in your NavHost:
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchMovieApp(
    navController: NavHostController,

) {


            NavHost(
                navController = navController,
                startDestination = AppScreen.SignInScreen().route,
           //   modifier = contentModifier.fillMaxSize(),
                enterTransition = { NavAnimations.slideInFromRight() },
                exitTransition = { NavAnimations.slideOutToLeft() },
                popEnterTransition = { NavAnimations.slideInFromRight() },
                popExitTransition = { NavAnimations.slideOutToLeft() }
            ) {
                composable(
                    AppScreen.SignInScreen().route,
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }
                ) {

                    SignInScreen(
                        navController = navController,
                        signUpNav = {
                            navController.navigate(AppScreen.SignUpScreen().route)
                        },
                        navToHomeScreen = {
                            navController.navigate(AppScreen.HomeScreen().route)
                        }
                    )
                }
                composable(
                    AppScreen.SignUpScreen().route,
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }

                ) {

                    SignUpScreen(
                        navController = navController,
                        navToHomeScreen = {
                            navController.navigate(
                                AppScreen.HomeScreen().route
                            )

                        }

                    )
                }





                // Home Screen Nav
                composable(
                    AppScreen.HomeScreen().route,
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }
                ) {
                   Scaffold(
                       topBar = {
                           TopAppBar(onSearchClick ={
                           navController.navigate(AppScreen.SearchScreen().route)})
                       }
                   ) {
                        HomeScreen(
                            modifier = Modifier.padding(top =25 .dp),
                            onMovieClick = {
                                navController.navigate(
                                    AppScreen.MovieDetailScreen().getRouteWithArgs(it)
                                ) {

                                }
                            },
                            onDiscoverArrowClick = {
                                navController.navigate(AppScreen.DiscoverMovieScreen().route) {
                                    launchSingleTop = true
                                    navController.navigate(AppScreen.HomeScreen().route)
                                }
                            },
                            onTradingArrowClick = {
                                navController.navigate(AppScreen.TrendingMovieScreen().route) {
                                    launchSingleTop = true
                                    navController.navigate(AppScreen.HomeScreen().route)

                                }

                            }

                        )
                   }

                }

                // Discover Movies Screen Nav
                composable(
                    AppScreen.DiscoverMovieScreen().route,
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(onSearchClick = {
                                navController.navigate(AppScreen.SearchScreen().route)
                            })
                        }
                    ) {
                        DiscoverMoviesScreen(
                            onMovieClick = {
                                navController.navigate(
                                    AppScreen.MovieDetailScreen().getRouteWithArgs(it)
                                ) {
                                    launchSingleTop = true
                                    navController.navigate(AppScreen.HomeScreen().route)

                                }
                            },
                            onNavigateUP = { navController.navigateUp() }
                        )
                    }
                }
                // Trending Movies Screen Nav
                composable(
                    AppScreen.TrendingMovieScreen().route,
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(onSearchClick ={
                                navController.navigate(AppScreen.SearchScreen().route)})
                        }
                    ) {
                    TrendingMoviesScreen(

                        onMovieClick = {
                            navController.navigate(
                                AppScreen.MovieDetailScreen().getRouteWithArgs(it)
                            ) {
                                launchSingleTop = true
                                navController.navigate(AppScreen.HomeScreen().route)

                            }
                        },
                        onNavigateUP = { navController.navigateUp() }
                    )
                }}

                // Film Screen Nav
                composable(
                    AppScreen.MovieDetailScreen().routeWithArgs,
                    arguments = listOf(navArgument(name = K.MOVIE_ID) {
                        type = NavType.IntType
                    }),
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(onSearchClick ={
                                navController.navigate(AppScreen.SearchScreen().route)})
                        }
                    ) {
                    MovieDetailScreen(
                        onNavigateUP = {
                            navController.navigateUp()
                        },
                        onMovieClick = {
                            navController.navigate(
                                AppScreen.MovieDetailScreen().getRouteWithArgs(it)
                            ) {
                                launchSingleTop = true
                                navController.navigate(AppScreen.HomeScreen().route)

                            }

                        },
                        onActorClick = {

                        }
                    )
                }}

                composable(
                    AppScreen.SearchScreen().route,
                    enterTransition = { NavAnimations.slideInFromRight() },
                    exitTransition = { NavAnimations.slideOutToLeft() }
                ) {

                    SearchScreen(
                        onMovieClick = {
                            navController.navigate(
                                AppScreen.MovieDetailScreen().getRouteWithArgs(it)
                            ) {
                                launchSingleTop = true
                                navController.navigate(AppScreen.HomeScreen().route)

                            }
                        },
                        onNavigateUP = { navController.navigateUp() }
                    )
                }
            }
        }

