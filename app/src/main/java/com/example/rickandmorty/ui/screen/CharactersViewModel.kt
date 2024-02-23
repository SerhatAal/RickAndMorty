package com.example.rickandmorty.ui.screen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.CharactersPagingSource
import com.example.rickandmorty.data.model.CharacterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersDataSource: CharactersPagingSource,
) : ViewModel() {

    val characters: Flow<PagingData<CharacterResponse>> = Pager(PagingConfig(pageSize = 20)) {
        charactersDataSource
    }.flow
}