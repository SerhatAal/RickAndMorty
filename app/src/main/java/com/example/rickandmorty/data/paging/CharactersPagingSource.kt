package com.example.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.model.character.CharacterResponse
import java.io.IOException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val nameQuery: String
) :
    PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {
        return try {
            val page = params.key ?: 1

            val response = apiService.getCharacters(nameQuery, page)
            val characters = response.body()?.results ?: emptyList()

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.body()?.info?.next != null) page + 1 else null

            LoadResult.Page(data = characters, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterResponse>): Int? {
        return state.anchorPosition
    }
}