package com.example.rickandmorty.data.model.episode

import com.example.rickandmorty.domain.model.Episode
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodeResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "air_date")
    val airDate: String,
    @Json(name = "episode")
    val episode: String,
    @Json(name = "characters")
    val characters: List<String>,
    @Json(name = "url")
    val url: String,
    @Json(name = "created")
    val created: String
)

fun EpisodeResponse.toDomainEpisode(): Episode {
    return Episode(
        characterIds = characters.map { it.substring(it.lastIndexOf("/") + 1).toInt() },
        id = id,
        name = name,
        airDate = airDate,
        episodeName = episode,
    )
}