package com.example.myapplication.di

import com.example.myapplication.common.data.ApiMapper
import com.example.myapplication.movie.data.mapper_impl.MovieApiMapperImpl
import com.example.myapplication.movie.data.remote.api.MovieApiService
import com.example.myapplication.movie.data.remote.models.MovieDto
import com.example.myapplication.movie.data.repository_impl.MovieRepositoryImpl
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie.domain.repository.MovieRepository
import com.example.myapplication.utils.K
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys =true
    }
    @Provides
    @Singleton
    fun provideMovieRepository(

        movieApiService: MovieApiService,
        mapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieRepository = MovieRepositoryImpl(movieApiService, mapper)


    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<List<Movie>, MovieDto> = MovieApiMapperImpl()


    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService{
        val contentType ="application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)


    }

}