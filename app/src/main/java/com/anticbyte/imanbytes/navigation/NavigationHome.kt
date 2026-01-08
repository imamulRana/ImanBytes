package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.home.HomeScreenRoute
import com.anticbyte.imanbytes.presentation.random_verse.RandomVerseScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeBaseRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeBaseRoute>(HomeRoute) {
        composable<HomeRoute> {
            HomeScreenRoute(navigateToRandomVerse = {
                navController.navigate(RandomVerseRoute(it))
            })
        }
        randomVerseGraph(navController)
    }
}