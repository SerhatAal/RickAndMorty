package com.example.rickandmorty.ui.screen.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                item?.let { location ->
                    LocationItem(location = location) {
                        onClick(location.id)
                    }
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

@Composable
fun LocationItem(location: Location, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClick),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type: ${location.type}",
                style = MaterialTheme.typography.body1,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Dimension: ${location.dimension}",
                style = MaterialTheme.typography.body1,
                color = Color.DarkGray
            )
        }
    }
}