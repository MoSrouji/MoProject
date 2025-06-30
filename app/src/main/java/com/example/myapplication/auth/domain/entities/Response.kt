package com.example.myapplication.auth.domain.entities

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val  data :T): Response<T>()
    data class Error(val massage: String): Response<Nothing>()
}