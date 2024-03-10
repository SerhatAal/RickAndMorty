package com.example.rickandmorty.ui.screen.episode

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
import com.example.rickandmorty.domain.model.Episode
import com.example.rickandmorty.ui.component.CircularLoadingIndicator
import com.example.rickandmorty.ui.component.ErrorMessage

@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier,
    episodesViewModel: EpisodesViewModel = hiltViewModel()
) {
    val episodes: LazyPagingItems<Episode> =
        episodesViewModel.episodesState.collectAsLazyPagingItems()
    EpisodesPagingList(pagingItems = episodes, onClick = {})
}

@Composable
fun EpisodesPagingList(pagingItems: LazyPagingItems<Episode>?, onClick: (Int) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        pagingItems?.itemCount?.let { itemCount ->
            items(itemCount) { index ->
                val item = pagingItems[index]
                item?.let { episode ->
                    EpisodeItem(episode = episode) {
                        onClick(episode.id)
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
fun EpisodeItem(episode: Episode, onItemClick: () -> Unit) {
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
                text = episode.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Episode: ${episode.episodeName}",
                style = MaterialTheme.typography.body1,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Air Date: ${episode.airDate}",
                style = MaterialTheme.typography.body1,
                color = Color.DarkGray
            )
        }
    }
}