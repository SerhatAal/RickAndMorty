package com.example.rickandmorty.data.model

import com.example.rickandmorty.domain.model.Location
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class LocationResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

fun LocationResponse.toDomainLocation(): Location {
    return Location(
        residentsIds = residents.map { it.substring(it.lastIndexOf("/") + 1).toInt() },
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}