package com.example.rickandmorty.ui.screen.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.repository.EpisodeRepository
import com.example.rickandmorty.domain.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: EpisodeRepository
) : ViewModel() {

    private val _episodesState: MutableStateFlow<PagingData<Episode>> =
        MutableStateFlow(value = PagingData.empty())
    val episodesState: MutableStateFlow<PagingData<Episode>> get() = _episodesState

    init {
        onEvent(EpisodesEvent.GetEpisodes)
    }

    private fun onEvent(event: EpisodesEvent) {
        viewModelScope.launch {
            when (event) {
                is EpisodesEvent.GetEpisodes -> {
                    getLocations()
                }
            }
        }
    }

    private suspend fun getLocations() {
        repository.getEpisodes()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _episodesState.value = it
            }
    }
}

sealed class EpisodesEvent {
    data object GetEpisodes : EpisodesEvent()
}