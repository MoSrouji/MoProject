package com.example.myapplication.ui.moviesScreenDiscoverAndTrending

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.navigation.NavAnimations
import com.example.myapplication.ui.components.LoadingView
import com.example.myapplication.ui.detail.DetailViewModel
import com.example.myapplication.ui.detail.WatchLaterUiState
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.home.itemSpacing
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DiscoverMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    movieDetailViewModel: DetailViewModel=hiltViewModel(),
    onMovieClick: (id: Int) -> Unit,
    onNavigateUP: () -> Unit,
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()
    val watchLaterState = movieDetailViewModel.usersState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val saveToWatchLaterLabel: String = "saveToWatchLater"


    Box(modifier = modifier.fillMaxWidth().padding(top = 50.dp)) {
        LaunchedEffect(watchLaterState) {
            when (watchLaterState) {  // Now using the actual state value
                is WatchLaterUiState.Success -> {
                    Toast.makeText(context, "Added", Toast.LENGTH_LONG).show()
                    movieDetailViewModel.resetState()
                }

                is WatchLaterUiState.Error -> {
                    Toast.makeText(
                        context,
                        watchLaterState.errorMessage,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    movieDetailViewModel.resetState()
                }

                else -> Unit
            }
        }
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
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(itemSpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = onNavigateUP) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back To Home Screen"

                        )
                    }
                    Text(
                        text = "Discover Movies ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }

                LazyColumn(modifier = Modifier.padding(vertical = itemSpacing)) {
                    items(state.discoverMovies) {
                        MovieCardShow(
                            movie = it,
                            onMovieClick = onMovieClick,
                            onSavedClick = {
                                val userId = FirebaseAuth.getInstance().currentUser?.uid
                                if (userId != null && state.movie != null) {
                                    movieDetailViewModel.addWatch(
                                        labelName = saveToWatchLaterLabel,
                                        movieId = state.movie!!.id
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        if (userId == null) "Please sign in" else "Movie data not available",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
    LoadingView(isLoading = state.isLoading)
}

