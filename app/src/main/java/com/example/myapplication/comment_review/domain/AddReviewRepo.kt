package com.example.myapplication.comment_review.domain

import com.example.myapplication.comment_review.domain.entities.MovieRating

interface MovieRatingRepository {
    suspend fun getMovieRating(movieId: Int): MovieRating?
    suspend fun rateMovie(movieId: Int, rating: Float)
    suspend fun getUserRating(movieId: Int): Float?
}