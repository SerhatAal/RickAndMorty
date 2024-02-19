package com.example.rickandmorty.data

import com.example.rickandmorty.data.api.ApiService
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val service: ApiService) {

    suspend fun getCharacterById(id: Int) = service.getCharacterById(id)
}