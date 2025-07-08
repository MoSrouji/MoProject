package com.example.myapplication.ui.movie_rating
// presentation/screens/MovieRatingScreen.kt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MovieRatingScreen(
    movieId: Int,
    viewModel: MovieRatingViewModel = hiltViewModel()
) {
    // Initialize with the movie ID
    LaunchedEffect(movieId) {
        viewModel.loadMovieRating(movieId)
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is MovieRatingUiState.Loading -> {
                CircularProgressIndicator()
            }
            is MovieRatingUiState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadMovieRating(movieId) }) {
                        Text("Retry")
                    }
                }
            }
            is MovieRatingUiState.Success -> {
                MovieRatingContent(
                    currentUserRating = state.currentUserRating,
                    averageRating = calculateAverageRating(state.movieRating?.userRating),
                    onRatingSelected = { rating -> viewModel.rateMovie(movieId, rating) }
                )
            }
        }
    }
}

@Composable
private fun MovieRatingContent(
    currentUserRating: Float?,
    averageRating: Float?,
    onRatingSelected: (Float) -> Unit
) {
    var selectedRating by remember { mutableStateOf(currentUserRating?.toInt() ?: 0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Rate this movie", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Rating selector
        RatingBar(
            rating = selectedRating,
            onRatingChanged = { selectedRating = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onRatingSelected(selectedRating.toFloat()) },
            enabled = selectedRating > 0
        ) {
            Text("Submit Rating")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display average rating if available
        averageRating?.let {
            Text(
                text = "Average Rating: ${"%.1f".format(it)}/5",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarOutline,
                contentDescription = "Rating $i",
                tint = if (i <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}

private fun calculateAverageRating(ratings: Map<String, Float>?): Float? {
    return ratings?.values?.takeIf { it.isNotEmpty() }?.average()?.toFloat()
}