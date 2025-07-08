package com.example.myapplication.auth.domain.entities

data class User(
    val userId: String?=null,
    val fullName: String?=null,
    val email: String?=null,
    val bio : String?=null,
    val saveToWatchLater: MutableMap<String , String> ? = mutableMapOf(),
    val saveToWatched: MutableMap<String , String> ? =mutableMapOf(),
    val searchHistory: MutableList<String > ? =mutableListOf(),
)
