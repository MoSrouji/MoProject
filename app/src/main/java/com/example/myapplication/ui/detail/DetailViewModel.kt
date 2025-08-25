package com.example.myapplication.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.comment_review.domain.MovieRatingRepository
import com.example.myapplication.comment_review.domain.entities.MovieRating
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie_detail.domain.models.MovieDetail
import com.example.myapplication.movie_detail.domain.repository.MovieDetailRepository
import com.example.myapplication.save_list.domain.repository.SaveListRepo
import com.example.myapplication.utils.K
import com.example.myapplication.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedstateHandle: SavedStateHandle,
    private val userRepository: SaveListRepo,
    private val rateRepository: MovieRatingRepository


) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    private val _userState = MutableStateFlow<WatchLaterUiState>(WatchLaterUiState.Idle)
    val userState: StateFlow<WatchLaterUiState> = _userState


    private val _usersState = MutableStateFlow<WatchLaterUiState>(WatchLaterUiState.Idle)
    val usersState: StateFlow<WatchLaterUiState> = _usersState

    private val _ratingState = MutableStateFlow<MovieRatingUiState>(MovieRatingUiState.Loading)
    val ratingState: StateFlow<MovieRatingUiState> = _ratingState.asStateFlow()

    private val _userReview = MutableStateFlow<String?>(null)
    val userReview: StateFlow<String?> = _userReview.asStateFlow()

    private val _userRating = MutableStateFlow<Float?>(null)
    val userRating: StateFlow<Float?> = _userRating.asStateFlow()



    fun addWatch(labelName: String, movieId: Int = id) {
        viewModelScope.launch {
            _userState.value = WatchLaterUiState.Loading
            try {
                userRepository.addWatch(
                    labelName = labelName,
                    movieId = movieId
                )
                _userState.value = WatchLaterUiState.Success("Added to Watch Later")
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error adding to watch later :${e.message}")
            }
        }
    }


    val id: Int = savedstateHandle.get<Int>(K.MOVIE_ID) ?: -1

    init {
        fetchMovieDetailById()
        loadMovieData()
    }


    fun fetchMovieDetailById() = viewModelScope.launch {
        if (id == -1) {
            _detailState.update {
                it.copy(
                    isLoading = false,
                    error = "Movie not found :)"
                )
            }
        } else {
            repository.fetchMovieDetail(id).collectAndHandle(
                onError = { error ->
                    _detailState.update {
                        it.copy(
                            isLoading = false,
                            error = error?.message
                        )
                    }

                },
                onLoading = {
                    _detailState.update {
                        it.copy(isLoading = true, error = null)
                    }
                },
                { movieDetail ->
                    _detailState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            movieDetail = movieDetail
                        )
                    }
                }
            )


        }
    }

    fun fetchMovie() = viewModelScope.launch {
        repository.fetchMovie().collectAndHandle(
            onError = { error ->
                _detailState.update {
                    it.copy(
                        isMovieLoading = false,
                        error = error?.message
                    )
                }

            },
            onLoading = {
                _detailState.update {
                    it.copy(isMovieLoading = true, error = null)
                }
            },
            { movies ->
                _detailState.update {
                    it.copy(
                        isMovieLoading = false,
                        error = null,
                        movies = movies
                    )
                }
            }
        )


    }

    fun resetState() {
        _userState.value = WatchLaterUiState.Idle
        _usersState.value = WatchLaterUiState.Idle
    }

    fun loadMovieData() {
        viewModelScope.launch {
            _ratingState.value = MovieRatingUiState.Loading
            try {
                val rating = rateRepository.getMovieRating(id)
                _userRating.value = rateRepository.getUserRating(id)
                _userReview.value = rateRepository.getUserReview(id)
                _ratingState.value = MovieRatingUiState.Success(rating)
            } catch (e: Exception) {
                _ratingState.value =
                    MovieRatingUiState.Error(e.message ?: "Failed to load movie data")
            }
        }
    }

    fun rateMovie(rating: Float) {

        viewModelScope.launch {
            try {
                rateRepository.rateMovie(id, rating)
                _userRating.value = rating
                //    loadMovieData() // Refresh data
            } catch (e: Exception) {
                _ratingState.value = MovieRatingUiState.Error(e.message ?: "Failed to rate movie")
            }
        }
    }

    fun submitReview(review: String) {
        viewModelScope.launch {
            try {
                rateRepository.addMovieReview(id, review)
                _userReview.value = review
                loadMovieData() // Refresh data
            } catch (e: Exception) {
                _ratingState.value =
                    MovieRatingUiState.Error(e.message ?: "Failed to submit review")
            }
        }
    }
}


data class DetailState(
    val movieDetail: MovieDetail? = null,
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isMovieLoading: Boolean = false,
    val success: Boolean = false
)

sealed class WatchLaterUiState {
    object Idle : WatchLaterUiState()
    object Loading : WatchLaterUiState()
    data class Success(val message: String) : WatchLaterUiState()
    data class Error(val errorMessage: String) : WatchLaterUiState()
}


sealed class MovieRatingUiState {
    object Loading : MovieRatingUiState()
    data class Success(val movieRating: MovieRating?) : MovieRatingUiState()
    data class Error(val message: String) : MovieRatingUiState()
}
