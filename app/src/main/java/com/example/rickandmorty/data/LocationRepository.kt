package com.example.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.model.toDomainLocation
import com.example.rickandmorty.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepository @Inject constructor(private val service: ApiService) {

    fun getLocations(): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                LocationsPagingSource(service)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomainLocation()
            }
        }
    }
}