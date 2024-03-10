package com.example.rickandmorty.domain.model

data class Episode(
    val id: Int,
    val characterIds: List<Int>,
    val name: String,
    val episodeName: String,
    val airDate: String,
)