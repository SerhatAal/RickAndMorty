package com.example.rickandmorty.ui.screen.character.detail

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.CharacterRepository
import com.example.rickandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characterDetail = MutableStateFlow<Character?>(null)
    val characterDetail = _characterDetail.asStateFlow()

    suspend fun getCharacterById(id: Int) {
        _characterDetail.value = repository.getCharacterById(id)
    }
}