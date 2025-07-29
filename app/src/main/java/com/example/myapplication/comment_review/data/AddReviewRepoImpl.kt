package com.example.myapplication.comment_review.data// data/repository/MovieRatingRepositoryImpl.kt
import com.example.myapplication.comment_review.domain.MovieRatingRepository
import com.example.myapplication.comment_review.domain.entities.MovieRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MovieRatingRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : MovieRatingRepository {
    companion object {
        private const val COLLECTION_NAME = "movieRatings"
    }


    override suspend fun getMovieRating(movieId: Int): MovieRating? {
        return try {
            val document = firestore.collection(COLLECTION_NAME)
                .document(movieId.toString())
                .get()
                .await()

            if (document.exists()) {
                MovieRating.fromMap(document.data!!)
            } else {
                null
            }
        } catch (e: Exception) {
            throw MovieRatingException("Failed to get movie rating", e)
        }
    }

    override suspend fun rateMovie(movieId: Int, rating: Float) {
        try {
            val userId = auth.currentUser?.uid ?: throw MovieRatingException("User not authenticated")
            updateMovieRating(movieId) { currentData ->
                val updatedRatings = currentData.userRating.toMutableMap().apply {
                    this[userId] = rating
                }
                currentData.copy(userRating = updatedRatings)
            }
        } catch (e: Exception) {
            throw MovieRatingException("Failed to rate movie", e)
        }
    }

    override suspend fun addMovieReview(movieId: Int, review: String) {
        try {
            val userId = auth.currentUser?.uid ?: throw MovieRatingException("User not authenticated")
            updateMovieRating(movieId) { currentData ->
                val updatedReviews = currentData.userReviews.toMutableMap().apply {
                    this[userId] = review
                }
                currentData.copy(userReviews = updatedReviews)
            }
        } catch (e: Exception) {
            throw MovieRatingException("Failed to add movie review", e)
        }
    }

    override suspend fun getUserRating(movieId: Int): Float? {
        val userId = auth.currentUser?.uid ?: return null
        return getMovieRating(movieId)?.userRating?.get(userId)
    }

    override suspend fun getUserReview(movieId: Int): String? {
        val userId = auth.currentUser?.uid ?: return null
        return getMovieRating(movieId)?.userReviews?.get(userId)
    }


    private suspend fun updateMovieRating(movieId: Int, update: (MovieRating) -> MovieRating) {
        val documentRef = firestore.collection(COLLECTION_NAME)
            .document(movieId.toString())

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(documentRef)
            val currentData = if (snapshot.exists()) {
                MovieRating.fromMap(snapshot.data!!)
            } else {
                MovieRating(movieId.toString())
            }
            transaction.set(documentRef, update(currentData).toMap())
        }.await()
    }
}


class MovieRatingException(message: String, cause: Throwable? = null) : Exception(message, cause)