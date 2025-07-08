package com.example.myapplication.ui.detail

import android.annotation.SuppressLint
import android.widget.Toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.navigation.NavAnimations
import com.example.myapplication.ui.components.LoadingView
import com.example.myapplication.ui.detail.components.DetailBodyContent
import com.example.myapplication.ui.detail.components.DetailTopContent
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnusedBoxWithConstraintsScope", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailViewModel: DetailViewModel = hiltViewModel(),
    onNavigateUP: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
) {


    val state = movieDetailViewModel.detailState.collectAsStateWithLifecycle().value

    val watchedState = movieDetailViewModel.userState.collectAsStateWithLifecycle().value
    val watchLaterState = movieDetailViewModel.usersState.collectAsStateWithLifecycle().value
    val saveToWatchLaterLabel: String = "saveToWatchLater"
    val saveToWatchedLabel: String = "saveToWatched"

    val context = LocalContext.current

    Box(modifier = modifier.fillMaxWidth()) {
        AnimatedVisibility(
            state.error != null,
            modifier = Modifier.align(Alignment.Center),
            enter = NavAnimations.slideInFromRight(),
            exit = NavAnimations.slideOutToLeft()
        ) {
            Text(
                state.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        AnimatedVisibility(
            visible = !state.isLoading && state.error == null,
            enter = NavAnimations.slideInFromRight(),
            exit = NavAnimations.slideOutToLeft()
        ) {


            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * 0.4f
                val bodyItemHeight = boxHeight * 0.6f
                state.movieDetail?.let { movieDetail ->
                    DetailTopContent(
                        movieDetail = movieDetail,
                        modifier = Modifier
                            .height(topItemHeight)
                            .align(Alignment.TopCenter)
                    )
                    // State observation
                    LaunchedEffect(watchLaterState) {
                        when (watchLaterState) {  // Now using the actual state value
                            is WatchLaterUiState.Success -> {
                                Toast.makeText(context, "Added", Toast.LENGTH_LONG).show()
                                movieDetailViewModel.resetState()
                            }

                            is WatchLaterUiState.Error -> {
                                Toast.makeText(context, watchLaterState.errorMessage, Toast.LENGTH_LONG)
                                    .show()
                                movieDetailViewModel.resetState()
                            }

                            else -> Unit
                        }
                    }     // State observation
                    LaunchedEffect(watchedState) {
                        when (watchedState) {  // Now using the actual state value
                            is WatchLaterUiState.Success -> {
                                Toast.makeText(context, "Added", Toast.LENGTH_LONG).show()
                                movieDetailViewModel.resetState()
                            }

                            is WatchLaterUiState.Error -> {
                                Toast.makeText(context, watchedState.errorMessage, Toast.LENGTH_LONG)
                                    .show()
                                movieDetailViewModel.resetState()
                            }

                            else -> Unit
                        }
                    }
                    DetailBodyContent(
                        movieDetail = movieDetail,
                        movies = state.movies,
                        isMovieLoading = state.isMovieLoading,
                        fetchMovies = movieDetailViewModel::fetchMovie,
                        onMovieClick = onMovieClick,
                        onActorClick = onActorClick,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(bodyItemHeight),
                        onBookMarkClick = {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            if (userId != null && state.movieDetail != null) {
                                movieDetailViewModel.addToWatchLater(
                                    labelName = saveToWatchLaterLabel,
                                    movieName = state.movieDetail.title,
                                    realiseDate = state.movieDetail.releaseDate
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    if (userId == null) "Please sign in" else "Movie data not available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, onWatchedClick = {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            if (userId != null && state.movieDetail != null) {
                                movieDetailViewModel.addToWatched(
                                    labelName = saveToWatchedLabel,
                                    movieName = state.movieDetail.title,
                                    realiseDate = state.movieDetail.releaseDate
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    if (userId == null) "Please sign in" else "Movie data not available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        watchLaterLoading = watchLaterState is WatchLaterUiState.Loading ,
                        watchedLoading= watchedState is WatchLaterUiState.Loading ,

                    )

                }

            }


        }


        IconButton(onClick = onNavigateUP, modifier = Modifier.align(Alignment.TopStart)) {

            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")

        }


    }
    LoadingView(isLoading = state.isLoading)


}


