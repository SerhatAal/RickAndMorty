package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.model.toDomainEpisode
import com.example.rickandmorty.data.paging.EpisodesPagingSource
import com.example.rickandmorty.domain.model.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val service: ApiService) {

    fun getEpisodes(): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                EpisodesPagingSource(service)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainEpisode()
            }
        }
    }
}