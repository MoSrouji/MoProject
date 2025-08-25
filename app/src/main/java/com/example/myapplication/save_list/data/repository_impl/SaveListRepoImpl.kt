package com.example.myapplication.save_list.data.repository_impl

import android.util.Log
import com.example.myapplication.auth.network.NetworkConstant
import com.example.myapplication.save_list.domain.repository.SaveListRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SaveListRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,

) : SaveListRepo {




    override suspend fun addToWatch(
        labelName: String,
        movieName: String,
        realiseDate: String
    ) {


        try {
            val updates = hashMapOf<String, Any>(
                "$labelName.$movieName" to realiseDate
            )
            db.collection(NetworkConstant.COLLECTION_NAME_USERS)
                .document(FirebaseAuth.getInstance().uid!!)
                .update(updates)
                .await()
        } catch (e: Exception) {
            throw e
        }


    }


    override suspend fun addWatch(
        labelName: String,
        movieId: Int
    ) {
        try {
            val updates = hashMapOf<String, Any>(
                labelName to FieldValue.arrayUnion(movieId)
            )
            db.collection(NetworkConstant.COLLECTION_NAME_USERS)
                .document(FirebaseAuth.getInstance().uid!!)
                .update(updates)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }




    override suspend fun searchHistory(searchMovie: String, labelName: String) {
        try {
            val userId = FirebaseAuth.getInstance().uid ?: throw Exception("User not authenticated")
            val userRef = db.collection(NetworkConstant.COLLECTION_NAME_USERS)
                .document(userId)

            // Use arrayUnion to add to a list without duplicates
            val updates = hashMapOf<String, Any>(
                labelName to FieldValue.arrayUnion(searchMovie)
            )

            userRef.update(updates).await()
        } catch (e: Exception) {
            throw Exception("Failed to update search history: ${e.message}")
        }
    }

    override suspend fun getSearchHistory(labelName: String): List<String> {
        return try {
            val userId = FirebaseAuth.getInstance().uid ?: return emptyList()
            val snapshot = db.collection(NetworkConstant.COLLECTION_NAME_USERS)
                .document(userId)
                .get()
                .await()

            snapshot.get(labelName) as? List<String> ?: emptyList()
        } catch (e: Exception) {
            throw Exception("Failed to get search history: ${e.message}")
        }
    }

    override suspend fun getSavedMovies(labelName: String): List<Int> {
        return try {
            val userId = FirebaseAuth.getInstance().uid ?: return emptyList()
            val snapshot = db.collection(NetworkConstant.COLLECTION_NAME_USERS)
                .document(userId)
                .get()
                .await()

            snapshot.get(labelName) as? List<Int> ?: emptyList()
        } catch (e: Exception) {
            throw Exception("Failed to get Saved Movies: ${e.message}")
        }
    }


    // To clear search history
    override suspend fun clearSearchHistory(labelName: String) {
        try {
            val userId = FirebaseAuth.getInstance().uid ?: throw Exception("User not authenticated")
            db.collection(NetworkConstant.COLLECTION_NAME_USERS)
                .document(userId)
                .update(labelName, emptyList<String>())
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to clear search history: ${e.message}")
        }
    }




}