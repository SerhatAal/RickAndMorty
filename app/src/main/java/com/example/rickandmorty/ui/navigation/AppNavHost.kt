package com.example.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmorty.ui.screen.character.CharacterScreen
import com.example.rickandmorty.ui.screen.character.detail.CharacterDetailScreen
import com.example.rickandmorty.ui.screen.episode.EpisodesScreen
import com.example.rickandmorty.ui.screen.location.LocationsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Characters.route,
        modifier = modifier
    ) {
        composable(route = Characters.route) {
            CharacterScreen(onClick = {
                navController.navigateToCharacterDetail(it)
            })
        }
        composable(route = Locations.route) {
            LocationsScreen()
        }
        composable(route = Episodes.route) {
            EpisodesScreen()
        }
        composable(
            route = "${CharacterDetail.route}/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("characterId")
                ?.let { characterId ->
                    CharacterDetailScreen(
                        characterId = characterId
                    )
                }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re-selecting the same item
        launchSingleTop = true
        // Restore state when re-selecting a previously selected item
        restoreState = true
    }

private fun NavHostController.navigateToCharacterDetail(characterId: Int) {
    this.navigateSingleTopTo("${CharacterDetail.route}/$characterId")
}