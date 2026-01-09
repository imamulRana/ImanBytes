package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anticbyte.imanbytes.presentation.asma.AsmaHusnaRoute
import kotlinx.serialization.Serializable

@Serializable
data object AsmaRoute

fun NavGraphBuilder.asmaGraph(navController: NavHostController) {
    composable<AsmaRoute> {
        AsmaHusnaRoute(navigateUp = { navController.navigateUp() })
    }
}