package com.example.rickandmorty.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            color = Color.Black
        )
        Text(
            text = characterProperties.description,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}