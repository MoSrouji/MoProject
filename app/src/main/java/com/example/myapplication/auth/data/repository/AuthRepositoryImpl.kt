package com.example.myapplication.auth.data.repository

import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.auth.domain.entities.User
import com.example.myapplication.auth.domain.repositories.AuthRepository
import com.example.myapplication.auth.network.NetworkConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth : FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): AuthRepository {
    override suspend fun firebaseSignUp(
        fullName: String,
        email: String,
        password: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)

        try {
            // Create auth user
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("User creation failed")

            // Create user profile in Firestore
            val userId = user.uid
            val userDoc = firebaseFirestore.collection(NetworkConstant.COLLECTION_NAME_USERS )
                .document(userId)

            // Verify document doesn't already exist
            if (userDoc.get().await().exists()) {
                // Clean up auth user if document exists
                user.delete().await()
                throw Exception("Sign up failed ")
            }

            // Create new document
            userDoc.set(
                User(
                    fullName = fullName,
                    email = email,
                    userId = userId
                )
            ).await()

            emit(Response.Success(true))

        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Sign up failed"))
            // Consider cleaning up any partially created resources here
        }


    }

    override suspend fun firebaseSignIn(
        email: String,
        password: String
    ): Flow<Response<Boolean>> = flow {

        emit(Response.Loading)

        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            // Additional checks if needed
            if (authResult.user != null) {
                emit(Response.Success(true))
            } else {
                emit(Response.Error("Sign in failed - no user returned"))
            }

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Sign in failed"))
        }
    }




}