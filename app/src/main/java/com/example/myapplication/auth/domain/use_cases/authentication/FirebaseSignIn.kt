package com.example.myapplication.auth.domain.use_cases.authentication

import com.example.myapplication.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String , password: String) =
        repository.firebaseSignIn(email=email ,password =password)
}