package com.anticbyte.imanbytes.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.anticbyte.imanbytes.presentation.player.PlayerViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationScreenRoute
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic.RecitationArRoute
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic.RecitationArViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation.RecitationTrRoute
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation.RecitationTrViewModel
import com.anticbyte.imanbytes.presentation.screens.selfRecitation.RecitationSelfDetailRoute
import com.anticbyte.imanbytes.presentation.screens.selfRecitation.RecitationSelfDetailViewModel
import com.anticbyte.imanbytes.presentation.screens.selfRecitation.RecitationSelfRoute
import com.anticbyte.imanbytes.presentation.screens.selfRecitation.RecitationSelfViewModel
import kotlinx.serialization.Serializable

@Serializable
data object RecitationBaseRoute

@Serializable
data object RecitationRoute

@Serializable
data object RecitationArRoute

@Serializable
data object RecitationTrRoute

@Serializable
data object RecitationSelfRoute

@Serializable
data class RecitationSelfDetailRoute(val surahNumber: String)

fun NavGraphBuilder.recitationGraph(
    navController: NavHostController
) {
    navigation<RecitationBaseRoute>(startDestination = RecitationRoute) {
        /* section for recitation main screen */
        composable<RecitationRoute> {
            RecitationScreenRoute(
                onNavigateBack = { navController.navigateUp() },
                navToArRecitation = { navController.navigate(RecitationArRoute) },
                navToTrRecitation = { navController.navigate(RecitationTrRoute) },
                navToSelfRecitation = { navController.navigate(RecitationSelfRoute) }
            )
        }
        /* section for arabic recitation */
        composable<RecitationArRoute> {
            val playerViewModel =
                hiltViewModel<PlayerViewModel>(LocalActivity.current as ComponentActivity)
            val viewModel = hiltViewModel<RecitationArViewModel>()
            RecitationArRoute(
                viewModel = viewModel,
                navigateBack = { navController.navigateUp() },
                /*navigateToReadSurah = { surahNumber ->
                    navController.navigate(
                        RecitationSelfDetailRoute(surahNumber = surahNumber)
                    )
                }*/
                playerViewModel = playerViewModel,
                navigateToReadSurah = { it }
            )
        }
        /* section for translation recitation */
        composable<RecitationTrRoute> {
            val playerViewModel =
                hiltViewModel<PlayerViewModel>(LocalActivity.current as ComponentActivity)
            val screenViewModel = hiltViewModel<RecitationTrViewModel>()
            RecitationTrRoute(
                playerViewModel = playerViewModel,
                screenViewModel = screenViewModel,
                onNavigateBack = { navController.navigateUp() },
                navigateToReadSurah = { surahNumber ->
                    navController.navigate(
                        RecitationSelfDetailRoute(surahNumber = surahNumber)
                    )
                })
        }
        /* section for self recitation */
        composable<RecitationSelfRoute> {
            val viewModel = hiltViewModel<RecitationSelfViewModel>()
            RecitationSelfRoute(
                viewModel = viewModel, navigateBack = { navController.navigateUp() },
                navigateToSurah = { surahNumber ->
                    navController.navigate(RecitationSelfDetailRoute(surahNumber = surahNumber))
                })
        }
        composable<RecitationSelfDetailRoute> {
            val viewModel = hiltViewModel<RecitationSelfDetailViewModel>()
            RecitationSelfDetailRoute(
                viewModel = viewModel,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}