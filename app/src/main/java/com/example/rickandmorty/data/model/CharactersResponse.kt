package com.example.rickandmorty.data.model

data class CharactersResponse(
    val info: Info,
    val results: List<CharacterResponse>
) {
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String? = null,
        val prev: String? = null
    )
}