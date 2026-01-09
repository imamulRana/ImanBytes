package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.random_verse.RandomVerseScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object RandomVerseBaseRoute

@Serializable
data class RandomVerseRoute(val verseId: String)

fun NavGraphBuilder.randomVerseGraph(navController: NavHostController) {
    navigation<RandomVerseBaseRoute>(startDestination = RandomVerseRoute::class) {
        composable<RandomVerseRoute> {
            RandomVerseScreenRoute(navigateUp = { navController.navigateUp() })
        }
    }
}