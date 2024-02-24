package com.example.rickandmorty.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.rickandmorty.R

interface BottomNavigationScreens {
    val route: String

    @get:StringRes
    val label: Int
    val icon: ImageVector
}

object Characters : BottomNavigationScreens {
    override val icon = Icons.Filled.Person
    override val label = R.string.characters
    override val route = "characters"
}

object Locations : BottomNavigationScreens {
    override val icon = Icons.Filled.Place
    override val label = R.string.locations
    override val route = "locations"
}

object Episodes : BottomNavigationScreens {
    override val icon = Icons.Filled.Share
    override val label = R.string.episodes
    override val route = "episodes"
}

object CharacterDetail : BottomNavigationScreens {
    override val icon = Icons.Filled.Share
    override val label = R.string.episodes
    override val route = "episodes"
}

val bottomBarRowScreens = listOf(Characters, Locations, Episodes)