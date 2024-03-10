package com.example.rickandmorty.data.model.episode

import com.example.rickandmorty.data.model.Info
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class EpisodesResponse(
    val info: Info,
    val results: List<EpisodeResponse>
)
