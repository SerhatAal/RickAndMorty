package com.example.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.model.episode.EpisodeResponse
import java.io.IOException
import javax.inject.Inject

class EpisodesPagingSource @Inject constructor(
    private val apiService: ApiService,
) :
    PagingSource<Int, EpisodeResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeResponse> {
        return try {
            val page = params.key ?: 1

            val response = apiService.getEpisodes(page)
            val episodes = response.body()?.results ?: emptyList()

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.body()?.info?.next != null) page + 1 else null

            LoadResult.Page(data = episodes, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeResponse>): Int? {
        return state.anchorPosition
    }
}