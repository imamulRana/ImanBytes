package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RecitationBaseRoute

@Serializable
data object RecitationArRoute

@Serializable
data object RecitationTrRoute

fun NavGraphBuilder.recitationNavGraph(navController: NavHostController) {
    composable<RecitationBaseRoute> {}
    composable<RecitationArRoute> {}
    composable<RecitationTrRoute> {}
}