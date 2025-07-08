package com.example.myapplication.auth.user_detail.domain.repository

import com.example.myapplication.auth.domain.entities.User


interface UserDetailRepo {
    suspend fun getCurrentUser(): User?
    suspend fun updateUser(user: User): Boolean
}