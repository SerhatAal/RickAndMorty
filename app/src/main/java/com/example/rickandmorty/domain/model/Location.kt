package com.example.rickandmorty.domain.model

data class Location(
    val id: Int,
    val residentsIds: List<Int>,
    val name: String,
    val dimension: String,
    val type: String,
)