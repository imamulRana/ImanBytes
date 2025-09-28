package com.anticbyte.imanbytes.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationScreenRoute
import com.anticbyte.imanbytes.presentation.screens.recitation.arabic.RecitationArRoute
import com.anticbyte.imanbytes.presentation.screens.recitation.arabic.RecitationArViewModel
import com.anticbyte.imanbytes.presentation.screens.recitation.translation.RecitationTrRoute
import com.anticbyte.imanbytes.presentation.screens.recitation.translation.RecitationTrViewModel
import kotlinx.serialization.Serializable

@Serializable
data object RecitationBaseRoute

@Serializable
data object RecitationRoute

@Serializable
data object RecitationArRoute

@Serializable
data object RecitationTrRoute

fun NavGraphBuilder.recitationNavGraph(navController: NavHostController) {
    navigation<RecitationBaseRoute>(startDestination = RecitationRoute) {
        composable<RecitationRoute> {
            RecitationScreenRoute(
                onNavigateBack = { navController.navigateUp() },
                navToArRecitation = { navController.navigate(RecitationArRoute) },
                navToTrRecitation = { navController.navigate(RecitationTrRoute) }
            )
        }
        composable<RecitationArRoute> {
            val viewModel = hiltViewModel<RecitationArViewModel>()
            RecitationArRoute(
                viewModel = viewModel,
                navigateBack = { navController.navigateUp() })
        }
        composable<RecitationTrRoute> {
            val viewModel = hiltViewModel<RecitationTrViewModel>()
            RecitationTrRoute(
                viewModel = viewModel,
                onNavigateBack = { navController.navigateUp() })
        }
    }
}