package com.example.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.model.toDomainCharacter
import com.example.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val service: ApiService) {

    fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                CharactersPagingSource(service)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainCharacter()
            }
        }
    }

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