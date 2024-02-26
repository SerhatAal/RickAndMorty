package com.example.rickandmorty.ui.screen.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.CharacterRepository
import com.example.rickandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charactersState: MutableStateFlow<PagingData<Character>> =
        MutableStateFlow(value = PagingData.empty())
    val charactersState: MutableStateFlow<PagingData<Character>> get() = _charactersState

    init {
        onEvent(CharactersEvent.GetCharacters)
    }

    private fun onEvent(event: CharactersEvent) {
        viewModelScope.launch {
            when (event) {
                is CharactersEvent.GetCharacters -> {
                    getCharacters()
                }
            }
        }
    }

    private suspend fun getCharacters() {
        repository.getCharacters()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _charactersState.value = it
            }
    }
}

sealed class CharactersEvent {
    data object GetCharacters : CharactersEvent()
}