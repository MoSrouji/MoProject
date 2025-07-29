package com.example.myapplication.comment_review.domain.entities

data class MovieRating(
    val movieID: String? = null,
    val userRating: Map<String, Float> = emptyMap() ,
    val userReviews: Map<String, String> = emptyMap()
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "movieId" to movieID!!,
            "userRatings" to userRating ,
            "userReviews" to userReviews

        )
    }

    companion object {
        fun fromMap(map: Map<String, Any>): MovieRating {
            return MovieRating(
                movieID = map["movieId"] as String,

                userRating = (map["userRatings"] as? Map<String, Double>)?.mapValues {
                    it.value.toFloat()
                } ?: emptyMap() ,
                userReviews = (map["userReviews"] as? Map<String, String>)?:emptyMap()
            )
        }

    }
}


