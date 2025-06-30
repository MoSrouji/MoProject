package com.example.myapplication.auth.domain.use_cases.authentication

import com.example.myapplication.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        password: String
    ) =repository.firebaseSignUp(fullName, email, password)

}