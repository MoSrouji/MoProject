package com.example.myapplication.movie.data.repository_impl

import com.example.myapplication.common.data.ApiMapper
import com.example.myapplication.movie.data.remote.api.MovieApiService
import com.example.myapplication.movie.data.remote.models.MovieDto
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie.domain.repository.MovieRepository
import com.example.myapplication.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val apiMapper: ApiMapper<List<Movie>, MovieDto>
) : MovieRepository {
    override suspend fun fetchDiscoverMovie(): Flow<Response<List<Movie>>> = flow {

        emit(Response.Loading())
        val movieDto = movieApiService.fetchDiscoverMovie()
        apiMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e->
        emit(Response.Error(e))

    }

    override suspend fun fetchTrendingMovie(): Flow<Response<List<Movie>>> = flow {

        emit(Response.Loading())
        val movieDto = movieApiService.fetchTrendingMovie()
        apiMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e->
        emit(Response.Error(e))

    }
}