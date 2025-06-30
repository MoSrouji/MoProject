package com.example.myapplication.di

import com.example.myapplication.save_list.data.repository_impl.UserRepoImpl
import com.example.myapplication.save_list.domain.repository.UserRepo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.jetbrains.annotations.NotNull;

@Module
@InstallIn(SingletonComponent::class)
object UserProvide {

    @Provides
    @Singleton
    fun provideUserRepo(db: @NotNull FirebaseFirestore): UserRepo{
        return UserRepoImpl(db)
    }

}