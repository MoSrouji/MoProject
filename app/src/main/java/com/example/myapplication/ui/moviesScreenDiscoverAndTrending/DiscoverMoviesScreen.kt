package com.example.myapplication.ui.moviesScreenDiscoverAndTrending

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.navigation.NavAnimations
import com.example.myapplication.ui.components.LoadingView
import com.example.myapplication.ui.home.HomeViewModel
import com.example.myapplication.ui.home.itemSpacing

@Composable
fun DiscoverMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit,
    onNavigateUP: () -> Unit,
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()

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
                        )
                    }
                }
            }
        }
    }
    LoadingView(isLoading = state.isLoading)
}

