package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeBaseRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeGraph() {
    navigation<HomeBaseRoute>(HomeRoute) {
        composable<HomeRoute> {
            HomeScreen()
        }
    }
}