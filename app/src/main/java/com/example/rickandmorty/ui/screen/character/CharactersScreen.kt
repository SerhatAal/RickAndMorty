package com.example.rickandmorty.ui.screen.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.ui.component.CharacterStatusComponent
import com.example.rickandmorty.ui.component.ErrorMessage
import com.example.rickandmorty.ui.component.SearchBarComponent

@Composable
fun CharacterScreen(
    onClick: (Int) -> Unit,
    charactersViewModel: CharactersViewModel = hiltViewModel()
) {
    val characters: LazyPagingItems<Character> =
        charactersViewModel.charactersState.collectAsLazyPagingItems()
    val searchQuery by charactersViewModel.nameSearchQuery.collectAsState()
    var isDeletingSearchQuery by remember { mutableStateOf(false) }

    Column {
        SearchBarComponent(
            query = searchQuery,
            onQueryChanged = { newQuery ->
                charactersViewModel.setSearchQuery(newQuery)
                isDeletingSearchQuery = newQuery.isEmpty()
            },
            placeholder = "Search character"
        )

        LaunchedEffect(searchQuery, isDeletingSearchQuery) {
            if (isDeletingSearchQuery) {
                charactersViewModel.getCharacters()
            } else if (searchQuery.isNotEmpty()) {
                charactersViewModel.getCharacters(searchQuery)
            }
        }

        when (characters.loadState.refresh) {
            is LoadState.Loading -> {
                CircularLoadingIndicator()
            }

            else -> {
                CharacterPagingList(pagingItems = characters, onClick = onClick)
            }
        }
    }
}

@Composable
fun CharacterPagingList(pagingItems: LazyPagingItems<Character>?, onClick: (Int) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        pagingItems?.itemCount?.let { itemCount ->
            items(itemCount) { index ->
                val item = pagingItems[index]
                item?.let { characters ->
                    CharacterItem(character = characters, onItemClick = onClick)
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
private fun CircularLoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CharacterItem(character: Character, onItemClick: (Int) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(character.id) },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = "Character Image",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                CharacterStatusComponent(characterStatus = character.status)
            }
            Image(
                imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                contentDescription = "Show ${character.name} detail"
            )
        }
    }
}

