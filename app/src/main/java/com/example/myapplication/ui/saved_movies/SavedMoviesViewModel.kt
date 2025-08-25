package com.example.myapplication.ui.saved_movies

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie_detail.domain.models.MovieDetail
import com.example.myapplication.movie_detail.domain.repository.MovieDetailRepository
import com.example.myapplication.save_list.domain.repository.SaveListRepo
import com.example.myapplication.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.awaitAll

@HiltViewModel
class SavedMoviesViewModel @Inject constructor(
    private val savedRepository: SaveListRepo ,
    private val repository : MovieDetailRepository
) : ViewModel() {



    private val _uiState = MutableStateFlow(SavedMoviesState())
    val uiState: StateFlow<SavedMoviesState> = _uiState.asStateFlow()

    val movieDetailList: MutableList<MovieDetail> = mutableListOf()

    init {
        loadSavedMovies()
    }

    fun loadSavedMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val savedMovies = savedRepository.getSavedMovies("saveToWatched")
                Log.d("SavedMovies", "Loaded saved movies: ${savedMovies.size}")

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movieIds = savedMovies,
                        isEmpty = savedMovies.isEmpty(),
                        error = null
                    )

                }
                if (savedMovies.isNotEmpty()){
                    fetchMovieDetails(savedMovies)
                }
            } catch (e: Exception) {
                Log.e("SavedMovies", "Error loading saved movies: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load saved movies",
                        isEmpty = true
                    )
                }
            }
        }
    }





    fun refresh() {
        loadSavedMovies()
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }


    private fun fetchMovieDetails(movieIds: List<Int>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isMovieLoading = true, error = null) }

            val movieDetails = mutableListOf<MovieDetail>()
            val errors = mutableListOf<String>()

            movieIds.forEach { movieId ->
                repository.fetchMovieDetail(movieId).collectAndHandle(
                    onError = { error ->
                        val errorMsg = "Failed to load movie $movieId: ${error?.message}"
                        errors.add(errorMsg)
                        Log.e("SavedMovies", errorMsg)
                    },
                    onLoading = {
                        // Individual movie loading state if needed
                    },
                     { movieDetail ->
                        movieDetails.add(movieDetail)
                        _uiState.update { currentState ->
                            currentState.copy(
                                movieDetailList = currentState.movieDetailList + movieDetail,
                                isMovieLoading = movieDetails.size < movieIds.size
                            )
                        }
                    }
                )
            }

            // Final state update
            _uiState.update {
                it.copy(
                    isMovieLoading = false,
                    error = if (errors.isNotEmpty()) errors.joinToString("\n") else null,
                    success = errors.isEmpty() && movieDetails.isNotEmpty()
                )
            }
        }
    }








    fun fetchMovieDetailById() = viewModelScope.launch {
        if (uiState.value.movieIds.isEmpty()) {
            _uiState.update {
                it.copy(
                    isMovieLoading  = false,
                    error = "Movie not found :)"
                )
            }
        } else {

           uiState.value.movieIds.forEach { ids ->
               repository.fetchMovieDetail(ids ).collectAndHandle(
                onError = { error ->
                    _uiState.update {
                        it.copy(
                            isMovieLoading = false,
                            error = error?.message
                        )
                    }

                },
                onLoading = {
                    _uiState.update {
                        it.copy(isMovieLoading = true, error = null)
                    }
                },
                { movieDetail ->

                    _uiState.update {
                        it.copy(
                            isMovieLoading = false,
                            error = null,
                            movieDetail = movieDetail ,
                        )

                    }
                    movieDetailList.add(movieDetail)

                }
            )


        }
        }
    }

}

data class SavedMoviesState(
    val isLoading: Boolean = false,
    val movieIds: List<Int> = emptyList(),
    val movieDetail: MovieDetail? = null,
    val movieDetailList:List<MovieDetail> =emptyList(),
    val isEmpty: Boolean = false,
    val error: String? = null ,
    val isMovieLoading: Boolean =false,
    val success: Boolean = false
)



