package com.example.rickandmorty.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.data.model.CharacterResponse

@Composable
fun CharacterScreen(
    charactersViewModel: CharactersViewModel = hiltViewModel()
) {
    val characters = charactersViewModel.characters.collectAsLazyPagingItems()
    CharacterPagingList(pagingItems = characters)
}


@Composable
fun CharacterPagingList(pagingItems: LazyPagingItems<CharacterResponse>?) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(Color.White)
    ) {
        item {
            Spacer(Modifier.height(4.dp))
        }
        if (pagingItems != null) {
            items(
                count = pagingItems.itemCount,
            ) { index ->
                val item = pagingItems[index]
                if (item != null) {
                    Text(text = item.name)
                }
            }
        }
        pagingItems?.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    //val error = pagingItems.loadState.refresh as LoadState.Error
                    item {
                        Text(text = "Error")
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    //val error = pagingItems.loadState.append as LoadState.Error
                    item {
                        Text(text = "Error")
                    }
                }
            }
        }
    }
}
