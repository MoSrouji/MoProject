package com.example.myapplication.ui.search


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.movie.domain.repository.MovieRepository
import com.example.myapplication.save_list.domain.repository.SaveListRepo
import com.example.myapplication.ui.home.HomeState
import com.example.myapplication.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository ,
    private val searchBrowserRepository : SaveListRepo
) : ViewModel() {

    private val _searchState = MutableStateFlow(HomeState())
    val searchState = _searchState.asStateFlow()

    var query by mutableStateOf("")

    var expanded by mutableStateOf(false)

    var browseHistory : List<String> = mutableListOf()

    fun onBtnCLick(){
        searchMovies(query)
        saveBrowseData()
    }



    fun onTextChanged(newText: String) {
        query = newText
    }
    fun onExpandedChanged(expand: Boolean) {
         expanded = expand
    }

init {
    getBrowserData()
}

    private fun searchMovies(movieName: String) = viewModelScope.launch {
        repository.searchMovie(movieName, "").collectAndHandle(
            onError = { error ->
                _searchState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _searchState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { movie ->

            _searchState.update {
                it.copy(
                    isLoading = false, error = null,
                    searchMovies = movie
                )
            }

        }
    }

    private fun saveBrowseData(){
        viewModelScope.launch {
            try {
                searchBrowserRepository.searchHistory(query, "searchHistory")
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun getBrowserData(){
        viewModelScope.launch {
            try {
               browseHistory = searchBrowserRepository.getSearchHistory("searchHistory")
            }catch (e: Exception){
            }
        }

    }


}