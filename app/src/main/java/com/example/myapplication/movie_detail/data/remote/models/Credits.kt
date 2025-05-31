package com.example.myapplication.movie_detail.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    @SerialName("cast")
    val castDto: List<CastDto?>? = null,
    @SerialName("crew")
    val crew: List<Crew?>? = null
)