package com.example.myapplication.ui.saved_movies

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.home.itemSpacing
import androidx.compose.runtime.getValue
import com.example.myapplication.movie_detail.domain.models.MovieDetail

@Composable
fun SavedMoviesScreen(
    viewModel: SavedMoviesViewModel = hiltViewModel(),
            onMovieClick :(Int)-> Unit,
    modifier: Modifier
) {
    val state by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize().padding(top=60.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(itemSpacing))
                Text("Loading ")
            }

            state.isEmpty -> {
                Text("No saved movies found")

            }

            state.error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${state.error}")
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Retry")
                    }
                }
            }
            else -> {
               // MovieIdsList(state.movieIds)
                Spacer(modifier = Modifier.padding(itemSpacing))


                when{
                   state.isMovieLoading ->{
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.padding(itemSpacing))
                        Text("Loading")

                    }
                    state.error!=null->{
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error: ${state.error}")
                            Button(onClick = { viewModel.refresh() }) {
                                Text("Retry")
                            }
                        }
                    }
                    else ->{
                        Spacer(modifier = Modifier.padding(itemSpacing))

                        ShowMovies(state.movieDetailList , onMovieClick)                    }
                }


            }
        }
    }
}

@Composable
fun MovieIdsList(movieIds: List<*>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(movieIds) { movieId ->
            Text(
                text = "Movie ID: ${movieId.toString()}", // Safe conversion
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ShowMovies(movies: List<MovieDetail> ,
               onMovieClick :(Int)-> Unit)
{

    LazyColumn(modifier = Modifier.padding(vertical = itemSpacing)) {
        items(movies){
            movie->
            MovieDetailCard(
                movie = movie,
                onMovieClick = onMovieClick
            )
        }



    }

}

