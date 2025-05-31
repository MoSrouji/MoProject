package com.example.myapplication.movie.data.mapper_impl

import com.example.myapplication.common.data.ApiMapper
import com.example.myapplication.movie.data.remote.models.MovieDto
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.utils.GenreConstants

class MovieApiMapperImpl: ApiMapper<List<Movie>, MovieDto>  {
    override fun mapToDomain(apiDto: MovieDto): List<Movie> {
        return apiDto.results?.map { result ->
            Movie(
                backdropPath = formatEmptyValue(result?.backdropPath),
                genreIds = formatGenre( result?.genreIds),
                id = result?.id?:0,
                originalLanguage = formatEmptyValue(result?.originalLanguage , default = "original Language"),
                originalTitle = formatEmptyValue(result?.originalTitle, default = "title"),
                overview = formatEmptyValue(result?.overview , default = "overview"),
                popularity = result?.popularity?:0.0,
                posterPath = formatEmptyValue(result?.posterPath , default = "posterPath"),
                releaseDate = formatEmptyValue(result?.releaseDate , default = "releaseDate"),
                title = formatEmptyValue(result?.title , default = "title"),
                voteAverage = result?.voteAverage?:0.0,
                voteCount = result?.voteCount?:0,
                video = result?.video?:false
                )

        }?:emptyList()

        }


    }
    private fun formatEmptyValue(value: String? , default: String=""): String {

if (value.isNullOrEmpty()) return "Unknown $default "
    return value
    }

    private fun formatGenre(genreId: List<Int?>?): List<String>{
        return genreId?.map { GenreConstants.getGenreNameById(it?:0) }?: emptyList()
    }


