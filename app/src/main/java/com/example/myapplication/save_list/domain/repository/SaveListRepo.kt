package com.example.myapplication.save_list.domain.repository

interface SaveListRepo{
    suspend fun addToWatch(
        labelName: String,
        movieName: String,
        realiseDate: String
    )

    suspend fun searchHistory(
        searchMovie: String ,
        labelName: String,


        )
    suspend fun clearSearchHistory(labelName: String)
    suspend fun getSearchHistory(labelName: String): List<String>

}