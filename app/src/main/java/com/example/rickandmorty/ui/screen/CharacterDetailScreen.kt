package com.example.rickandmorty.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.rickandmorty.domain.model.CharacterStatus
import com.example.rickandmorty.ui.component.CharacterProperties
import com.example.rickandmorty.ui.component.CharacterPropertyComponent

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    characterId: Int,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    val character by viewModel.characterDetail.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getCharacterById(characterId)
    }

    character?.let { characterDetail ->
        LazyColumn(
            modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(all = 16.dp)
        ) {

            item {
                CharacterName(name = characterDetail.name)
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                CharacterImage(imageUrl = characterDetail.imageUrl)
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                CharacterPropertyComponent(
                    characterProperties = CharacterProperties(
                        title = "Species",
                        description = characterDetail.species
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                CharacterPropertyComponent(
                    characterProperties = CharacterProperties(
                        title = "Gender",
                        description = characterDetail.gender.displayName
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                CharacterPropertyComponent(
                    characterProperties = CharacterProperties(
                        title = "Origin",
                        description = characterDetail.origin.name
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                CharacterPropertyComponent(
                    characterProperties = CharacterProperties(
                        title = "Location",
                        description = characterDetail.location.name
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                CharacterPropertyComponent(
                    characterProperties = CharacterProperties(
                        title = "Number of episodes",
                        description = characterDetail.episodeIds.size.toString()
                    )
                )
            }
        }
    }
}

@Composable
fun CharacterName(name: String) {
    Text(
        text = name,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun CharacterImage(
    imageUrl: String,
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Character image",
        loading = { CircularProgressIndicator() },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp)),
    )
}

@Composable
fun CharacterStatusComponent(
    characterStatus: CharacterStatus,
) {
    Row {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(4.dp)
                .background(color = characterStatus.color, shape = CircleShape)
        )
        CharacterPropertyComponent(
            characterProperties = CharacterProperties(
                title = "Status",
                description = characterStatus.displayName
            )
        )
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreview() {
    CharacterStatusComponent(characterStatus = CharacterStatus.Alive)
}
