package com.example.myapplication.auth.domain.repositories

import com.example.myapplication.auth.domain.entities.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun firebaseSignUp(
        fullName: String ,
        email: String ,
        password: String): Flow<Response<Boolean>>

    suspend fun firebaseSignIn(
        email: String ,
        password: String
    ): Flow<Response<Boolean>>

}