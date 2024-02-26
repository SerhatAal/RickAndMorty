package com.example.rickandmorty.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            fontSize = 24.sp,
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