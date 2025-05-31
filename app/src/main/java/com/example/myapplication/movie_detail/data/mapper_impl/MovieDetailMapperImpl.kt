package com.example.myapplication.movie_detail.data.mapper_impl

import com.example.myapplication.common.data.ApiMapper
import com.example.myapplication.movie_detail.data.remote.models.MovieDetailDto
import com.example.myapplication.movie_detail.domain.models.MovieDetail
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.myapplication.movie_detail.domain.models.Cast
import com.example.myapplication.movie_detail.domain.models.Review
import com.example.myapplication.movie_detail.data.remote.models.CastDto
class MovieDetailMapperImpl: ApiMapper<MovieDetail, MovieDetailDto> {
    override fun mapToDomain(apiDto: MovieDetailDto): MovieDetail {
      return MovieDetail(
          backdropPath = formatEmptyValue(apiDto.backdropPath),
          genreIds = apiDto.genres?.map { formatEmptyValue(it?.name)  } ?: emptyList(),
          id = apiDto.id ?: 0,
          originalLanguage = formatEmptyValue(apiDto.originalLanguage ,"Language"),
          originalTitle = formatEmptyValue(apiDto.originalTitle,"title"),
          overview = formatEmptyValue(apiDto.overview,"overview"),
          popularity = apiDto.popularity ?: 0.0,
          posterPath = formatEmptyValue(apiDto.posterPath),
          releaseDate = formatTimeStamp(time = apiDto.releaseDate ?: "date"),
          title =  formatEmptyValue(apiDto.title ,"title"),
          voteAverage = apiDto.voteAverage ?: 0.0,
          voteCount = apiDto.voteCount ?: 0,
          cast = formatCast(apiDto.credits?.castDto),
          language = apiDto.spokenLanguages?.map { formatEmptyValue(it?.englishName) } ?: emptyList(),
          productionCountry = apiDto.productionCountries?.map { formatEmptyValue(it?.name) } ?: emptyList(),
          reviews = apiDto.reviews?.results?.map {
              Review(
                  author =formatEmptyValue(it?.author),
                  content = formatEmptyValue(it?.content),
                  id = formatEmptyValue(it?.id),
                  createdAt = formatTimeStamp(time = it?.createdAt ?: "0"),
                  rating = it?.authorDetails?.rating ?:0.0)
          }?:emptyList(),
          runtime = convertMinutesToHours(apiDto.runtime ?:0)
      )
    }
    private fun formatTimeStamp(pattern: String ="dd.MM.yy",time: String): String{
        val inputDataFormatter=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputDateFormatter=SimpleDateFormat(
            pattern, Locale.getDefault())
        val date = inputDataFormatter.parse(time)

        val formattedDate = date?.let { outputDateFormatter.format(it) }?:time
        return formattedDate

    }

    private fun convertMinutesToHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h:${remainingMinutes}m"
    }
    private fun formatEmptyValue(value:String? , default: String=" "  ): String{
        if (value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatCast(castDto: List<CastDto?>?): List<Cast>{
        return castDto?.map {
            val genderRole = if (it?.gender==2) "Actor" else "Actress"
            Cast(
                id = it?.id ?: 0,
                name = it?.name ?: "Unknown",
                genreRole = genderRole,
                character = it?.character ?: "Unknown",
                profilePath = it?.profilePath ?: ""
            )
        }?:emptyList()
    }



}


