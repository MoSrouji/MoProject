package com.example.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie.domain.repository.MovieRepository
import com.example.myapplication.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository ,

): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()


    init {
        fetchDiscoverMovies()
    }
    init {
        fetchTrendingMovies()
    }



    private fun fetchDiscoverMovies() =viewModelScope.launch {
        repository.fetchDiscoverMovie().collectAndHandle(
         onError = {error->_homeState.update {
             it.copy(isLoading=false, error = error?.message)
         }} ,
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) {movie->

            _homeState.update {
                it.copy(
                    isLoading = false
                    , error = null
                    , discoverMovies = movie
                )
            }

        }

    }



    private fun fetchTrendingMovies() =viewModelScope.launch {
        repository.fetchTrendingMovie().collectAndHandle(
         onError = {error->_homeState.update {
             it.copy(isLoading=false, error = error?.message)
         }} ,
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) {movie->

            _homeState.update {
                it.copy(
                    isLoading = false
                    , error = null
                    , trendingMovies = movie
                )
            }

        }

    }

    // to handle movie selection
fun selectedMovie(movie: Movie){
    _homeState.update {
        it.copy(movie = movie)
    }
}
    fun getSelectedMovie(): Movie?{
        return _homeState.value.movie
    }
    fun newfun(){
        getSelectedMovie()?.title
    }


}

data class HomeState(
    val movie: Movie ?=null,
    val searchMovies: List<Movie> = emptyList(),
    val discoverMovies: List<Movie> = emptyList(),
    val trendingMovies: List<Movie> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false

)