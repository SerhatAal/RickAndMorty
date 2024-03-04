package com.example.rickandmorty.data.model

data class LocationsResponse(
    val info: Info,
    val results: List<LocationResponse>
) {
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String? = null,
        val prev: String? = null
    )
}