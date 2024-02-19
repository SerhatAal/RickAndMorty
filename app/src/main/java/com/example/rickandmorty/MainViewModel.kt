package com.example.rickandmorty

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.CharacterRepository
import com.example.rickandmorty.data.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    suspend fun getCharacterById(id: Int): Character {
        try {
            val response = repository.getCharacterById(id)
            if (response.isSuccessful) {
                return response.body() ?: throw NoSuchElementException("Character not found")
            } else {
                throw IOException("Error getting character. HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}