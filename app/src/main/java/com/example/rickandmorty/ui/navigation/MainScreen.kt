package com.example.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun MainScreen() {
    RickAndMortyTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavBar(navController = navController, allScreens = bottomBarRowScreens)
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}