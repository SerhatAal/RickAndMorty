package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("${Constants.CHARACTER_END_POINT}/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Character>
}