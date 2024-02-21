package com.example.rickandmorty.data

import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.model.toDomainCharacter
import com.example.rickandmorty.domain.model.Character
import java.io.IOException
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val service: ApiService) {

    suspend fun getCharacterById(id: Int): Character {
        try {
            val response = service.getCharacterById(id)
            if (response.isSuccessful) {
                return response.body()?.toDomainCharacter()
                    ?: throw NoSuchElementException("Character not found")
            } else {
                throw IOException("Error getting character. HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}