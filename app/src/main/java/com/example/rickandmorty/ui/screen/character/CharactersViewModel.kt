package com.example.rickandmorty.ui.screen.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.CharacterRepository
import com.example.rickandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charactersState: MutableStateFlow<PagingData<Character>> =
        MutableStateFlow(value = PagingData.empty())
    val charactersState: MutableStateFlow<PagingData<Character>> get() = _charactersState

    private val _nameSearchQuery = MutableStateFlow("")
    val nameSearchQuery = _nameSearchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

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

    suspend fun getCharacters(name: String = "") {
        val pagingData = if (name.isEmpty()) {
            repository.getCharacters(name)
        } else {
            repository.getCharacters(name)
        }

        pagingData
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _charactersState.value = it
            }
    }

    fun setSearchQuery(query: String) {
        _nameSearchQuery.value = query
        onEvent(CharactersEvent.GetCharacters)
    }
}

sealed class CharactersEvent {
    data object GetCharacters : CharactersEvent()
}