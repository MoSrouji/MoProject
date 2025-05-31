package com.example.myapplication.movie.domain.repository

import com.example.myapplication.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.utils.Response
interface MovieRepository {
    suspend fun fetchDiscoverMovie(): Flow<Response< List<Movie>>>
    suspend fun fetchTrendingMovie(): Flow<Response< List<Movie>>>

}