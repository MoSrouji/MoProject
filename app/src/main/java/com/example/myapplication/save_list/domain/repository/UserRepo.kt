package com.example.myapplication.save_list.domain.repository

interface UserRepo{
    suspend fun addToWatch(
        labelName: String,
        movieName: String,
        realiseDate: String
    )
}