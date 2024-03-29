package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.character.CharacterResponse
import com.example.rickandmorty.data.model.character.CharactersResponse
import com.example.rickandmorty.data.model.episode.EpisodesResponse
import com.example.rickandmorty.data.model.location.LocationsResponse
import com.example.rickandmorty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.CHARACTER_END_POINT)
    suspend fun getCharacters(
        @Query("name") characterName: String = "",
        @Query("page") page: Int
    ): Response<CharactersResponse>

    @GET("${Constants.CHARACTER_END_POINT}/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterResponse>

    @GET(Constants.LOCATION_END_POINT)
    suspend fun getLocations(
        @Query("page") page: Int
    ): Response<LocationsResponse>

    @GET(Constants.EPISODE_END_POINT)
    suspend fun getEpisodes(
        @Query("page") page: Int
    ): Response<EpisodesResponse>
}