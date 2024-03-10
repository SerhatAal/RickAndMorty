package com.example.rickandmorty.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.domain.model.CharacterStatus

data class CharacterProperties(
    val title: String,
    val description: String
)

@Composable
fun CharacterPropertyComponent(characterProperties: CharacterProperties) {
    Column {
        Text(
            text = characterProperties.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = characterProperties.description,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
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
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = characterStatus.displayName,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreview() {
    CharacterStatusComponent(characterStatus = CharacterStatus.Alive)
}