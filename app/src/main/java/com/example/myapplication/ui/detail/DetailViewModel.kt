package com.example.myapplication.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedstateHandle: SavedStateHandle,
    private val userRepository: SaveListRepo

) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    private val _userState = MutableStateFlow<WatchLaterUiState>(WatchLaterUiState.Idle)
    val userState: StateFlow<WatchLaterUiState> = _userState


    private val _usersState = MutableStateFlow<WatchLaterUiState>(WatchLaterUiState.Idle)
    val usersState: StateFlow<WatchLaterUiState> = _usersState


    fun addToWatched(labelName: String, movieName: String, realiseDate: String) {
        viewModelScope.launch {
            _userState.value = WatchLaterUiState.Loading
            try {
                userRepository.addToWatch(
                    labelName = labelName,
                    movieName = movieName,
                    realiseDate=realiseDate)
                _userState.value = WatchLaterUiState.Success("Added to watch later")
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error adding to watch later: ${e.message}")
                _userState.value =
                    WatchLaterUiState.Error("Failed to add'$movieName' to watch later . please try again ")
            }
        }
    }
    fun addToWatchLater(labelName: String, movieName: String, realiseDate: String) {
        viewModelScope.launch {
            _usersState.value = WatchLaterUiState.Loading
            try {
                userRepository.addToWatch(
                    labelName = labelName,
                    movieName = movieName,
                    realiseDate=realiseDate)
                _usersState.value = WatchLaterUiState.Success("Added to watch later")
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error adding to watch later: ${e.message}")
                _userState.value =
                    WatchLaterUiState.Error("Failed to add'$movieName' to watch later . please try again ")
            }
        }
    }

    val id: Int = savedstateHandle.get<Int>(K.MOVIE_ID) ?: -1


    init {
        fetchMovieDetailById()
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
    fun resetState(){
        _userState.value = WatchLaterUiState.Idle
        _usersState.value = WatchLaterUiState.Idle
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