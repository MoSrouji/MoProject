package com.example.myapplication.auth.user_detail.data.repository_impl

import com.example.myapplication.auth.domain.entities.User
import com.example.myapplication.auth.network.NetworkConstant.COLLECTION_NAME_USERS
import com.example.myapplication.auth.user_detail.domain.repository.UserDetailRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDetailRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserDetailRepo {
    override suspend fun getCurrentUser(): User? {


        val currentUser = auth.currentUser ?: return null

        return try {
            val document =
                db.collection(COLLECTION_NAME_USERS).document(currentUser.uid).get().await()

            document.toObject(User::class.java)?.copy(userId = currentUser.uid)


        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }



    override suspend fun updateUser(user: User? , newEmail: String?): Boolean {
       val currentUser = auth.currentUser?:return false

        return try {
            newEmail.let {
                currentUser.updateEmail(user?.email!!).await()
            }

            db.collection(COLLECTION_NAME_USERS).document(user?.userId!!).set(user).await()

            true
        }catch (e: Exception) {
            e.printStackTrace()
            false
        }



    }
}