package com.example.myapplication.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.TopAppBar
import com.example.myapplication.ui.detail.MovieDetailScreen
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.moviesScreenDiscoverAndTrending.DiscoverMoviesScreen
import com.example.myapplication.ui.moviesScreenDiscoverAndTrending.TrendingMoviesScreen
import com.example.myapplication.utils.K

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchMovieApp(
    navController: NavHostController,
    modifier : Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopAppBar() }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen().route,
            modifier = contentModifier.fillMaxSize()
        ) {
            //Home Screen Nav
            composable(
                Route.HomeScreen().route,
                enterTransition = { fadeIn() + scaleIn() },
                exitTransition = { fadeOut() + shrinkOut() }) {
                HomeScreen(
                    onMovieClick = {
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(it)
                        ) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                            }
                        }
                    },
                    onDiscoverArrowClick = {
                        navController.navigate(Route.DiscoverMoviesScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                            }
                        }
                    },
                    onTradingArrowClick = {
                        navController.navigate(Route.TrendingMoviesScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                            }
                        }

                    }

                )
            }

            //Discover Movies Screen Nav
            composable(
                Route.DiscoverMoviesScreen().route,
                enterTransition = { fadeIn() + scaleIn() },
                exitTransition = { fadeOut() + shrinkOut() }
            ) {

                DiscoverMoviesScreen(
                    onMovieClick = {
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(it)
                        ) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                            }
                        }
                    },
                    onNavigateUP = { navController.navigateUp() }
                )
            }

            //Trending Movies Screen Nav
            composable(
                Route.TrendingMoviesScreen().route,
                enterTransition = { fadeIn() + scaleIn() },
                exitTransition = { fadeOut() + shrinkOut() }
            ) {

                TrendingMoviesScreen(
                        onMovieClick = {
                            navController.navigate(
                                Route.FilmScreen().getRouteWithArgs(it)
                            ) {
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = false
                                }
                            }
                        },
                        onNavigateUP = { navController.navigateUp() }
                    )
            }



                        //Film Screen Nav
                        composable(
                            Route.FilmScreen().routeWithArgs,
                            arguments = listOf(navArgument(name = K.MOVIE_ID) {
                                type = NavType.IntType
                            }),
                            enterTransition = { fadeIn() + scaleIn() },
                            exitTransition = { fadeOut() + shrinkOut() }) {
                            MovieDetailScreen(
                                onNavigateUP = {
                                    navController.navigateUp()
                                },
                                onMovieClick = {
                                    navController.navigate(
                                        Route.FilmScreen().getRouteWithArgs(it)
                                    ) {
                                        launchSingleTop = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            inclusive = false
                                        }
                                    }

                                },
                                onActorClick = {

                                }

                            )

                        }

                    }
            }
        }
