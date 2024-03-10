package com.example.rickandmorty.data.model.character

import com.example.rickandmorty.data.model.Info
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class CharactersResponse(
    val info: Info,
    val results: List<CharacterResponse>
)