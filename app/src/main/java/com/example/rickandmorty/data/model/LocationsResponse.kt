package com.example.rickandmorty.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class LocationsResponse(
    val info: Info,
    val results: List<LocationResponse>
)