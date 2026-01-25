package com.anticbyte.imanbytes.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.home.HomeScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeBaseRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    navigation<HomeBaseRoute>(HomeRoute) {
        composable<HomeRoute> {
            HomeScreenRoute(modifier = modifier, navigateToRandomVerse = {
                navController.navigate(RandomVerseRoute(it))
            })
        }
    }
}