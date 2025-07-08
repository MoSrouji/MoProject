package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.comment_review.data.MovieRatingRepositoryImpl
import com.example.myapplication.comment_review.domain.MovieRatingRepository
import com.example.myapplication.save_list.data.repository_impl.SaveListRepoImpl
import com.example.myapplication.save_list.domain.repository.SaveListRepo
import com.example.myapplication.auth.user_detail.data.repository_impl.UserDetailRepoImpl
import com.example.myapplication.auth.user_detail.domain.repository.UserDetailRepo
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {


    @Singleton
    @Provides
    fun provideFirebaseAuth(application: Application): FirebaseAuth {
        FirebaseApp.initializeApp(application)
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideMovieRatingRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): MovieRatingRepository {
        return MovieRatingRepositoryImpl(firestore, auth)
    }


    @Provides
    @Singleton
    fun provideUserDetailRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): UserDetailRepo = UserDetailRepoImpl(auth, db)

    @Provides
    @Singleton
    fun provideSaveListRepo(db: @NotNull FirebaseFirestore): SaveListRepo {
        return SaveListRepoImpl(db)
    }

}


