package com.example.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.model.CharacterResponse
import java.io.IOException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(private val repository: CharacterRepository) :
    PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {
        return try {
            val page = params.key ?: 1

            val response = repository.getCharacters(page)
            val characters = response.body()?.results ?: emptyList()

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (response.body()?.info?.next != null) page + 1 else null

            LoadResult.Page(data = characters, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}