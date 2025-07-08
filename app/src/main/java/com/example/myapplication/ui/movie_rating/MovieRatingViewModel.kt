package com.example.myapplication.ui.movie_rating
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.comment_review.domain.MovieRatingRepository
import com.example.myapplication.comment_review.domain.entities.MovieRating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieRatingViewModel @Inject constructor(
    private val repository: MovieRatingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MovieRatingUiState>(MovieRatingUiState.Loading)
    val uiState: StateFlow<MovieRatingUiState> = _uiState.asStateFlow()

    fun loadMovieRating(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieRatingUiState.Loading
            try {
                val currentUserRating = repository.getUserRating(movieId)
                val movieRating = repository.getMovieRating(movieId)

                _uiState.value = MovieRatingUiState.Success(
                    movieRating = movieRating,
                    currentUserRating = currentUserRating
                )
            } catch (e: Exception) {
                _uiState.value = MovieRatingUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun rateMovie(movieId: Int, rating: Float) {
        viewModelScope.launch {
            try {
                repository.rateMovie(movieId, rating)
                loadMovieRating(movieId) // Refresh the data
            } catch (e: Exception) {
                _uiState.value = MovieRatingUiState.Error(e.message ?: "Failed to submit rating")
            }
        }
    }
}
sealed class MovieRatingUiState {
    object Loading : MovieRatingUiState()
    data class Success(
        val movieRating: MovieRating?,
        val currentUserRating: Float?
    ) : MovieRatingUiState()
    data class Error(val message: String) : MovieRatingUiState()
}