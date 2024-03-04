package com.example.rickandmorty.ui.screen.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.domain.model.Location
import com.example.rickandmorty.ui.component.CircularLoadingIndicator
import com.example.rickandmorty.ui.component.ErrorMessage

@Composable
fun LocationsScreen(
    modifier: Modifier = Modifier,
    locationsViewModel: LocationsViewModel = hiltViewModel()
) {
    val locations: LazyPagingItems<Location> =
        locationsViewModel.locationsState.collectAsLazyPagingItems()
    LocationsPagingList(pagingItems = locations, onClick = {})
}

@Composable
fun LocationsPagingList(pagingItems: LazyPagingItems<Location>?, onClick: (Int) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        pagingItems?.itemCount?.let { itemCount ->
            items(itemCount) { index ->
                val item = pagingItems[index]
                item?.let { locations ->
                    Text(text = locations.name)
                }
            }
        }
        pagingItems?.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularLoadingIndicator()
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        CircularLoadingIndicator()
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = pagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.padding(4.dp)) }
    }
}