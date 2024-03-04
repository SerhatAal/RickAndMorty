package com.example.rickandmorty.ui.screen.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.LocationRepository
import com.example.rickandmorty.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _locationsState: MutableStateFlow<PagingData<Location>> =
        MutableStateFlow(value = PagingData.empty())
    val locationsState: MutableStateFlow<PagingData<Location>> get() = _locationsState

    init {
        onEvent(LocationsEvent.GetLocations)
    }

    private fun onEvent(event: LocationsEvent) {
        viewModelScope.launch {
            when (event) {
                is LocationsEvent.GetLocations -> {
                    getLocations()
                }
            }
        }
    }

    private suspend fun getLocations() {
        repository.getLocations()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _locationsState.value = it
            }
    }
}

sealed class LocationsEvent {
    data object GetLocations : LocationsEvent()
}