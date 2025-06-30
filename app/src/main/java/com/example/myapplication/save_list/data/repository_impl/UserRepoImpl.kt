package com.example.myapplication.save_list.data.repository_impl

import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.auth.network.NetworkConstant
import com.example.myapplication.save_list.domain.repository.UserRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : UserRepo {
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

}