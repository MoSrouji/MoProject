package com.example.myapplication.movie_detail.domain.models



data class MovieDetail(
    val backdropPath: String,
    val genreIds: List<String>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val cast: List<Cast>,
    val language: List<String>,
    val productionCountry: List<String>,
    val reviews: List<Review>,
    val runtime: String
)

data class Cast(
    val id: Int,
    val name: String,
    val genreRole: String,
    val character: String,
    val profilePath: String

) {
    private val nameParts = name.split(" " , limit = 2)
    val firstName = nameParts.first()
    val lastName = nameParts.last()

}


data class Review(

    val author: String,
    val content: String,
    val id: String,
    val createdAt: String,
    val rating: Double

)


