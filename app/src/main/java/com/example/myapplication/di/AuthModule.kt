package com.example.myapplication.di

import com.example.myapplication.auth.domain.repositories.AuthRepository
import com.example.myapplication.auth.data.repository.AuthRepositoryImpl
import com.example.myapplication.auth.domain.use_cases.authentication.AuthUseCases
import com.example.myapplication.auth.domain.use_cases.authentication.FirebaseSignIn
import com.example.myapplication.auth.domain.use_cases.authentication.FirebaseSignUp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): AuthRepository     {

        return AuthRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }



    @Singleton
    @Provides
    fun provideAuthenticationUseCases(
        repository: AuthRepository
    ) = AuthUseCases(firebaseSignUp = FirebaseSignUp(repository = repository),
        firebaseSignIn = FirebaseSignIn(repository = repository))
}