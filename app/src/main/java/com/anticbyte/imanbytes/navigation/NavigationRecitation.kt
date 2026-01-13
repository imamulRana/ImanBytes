package com.anticbyte.imanbytes.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationScreenRoute
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic.RecitationArRoute
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation.RecitationTrRoute
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
        val ca = LocalActivity.current as ComponentActivity
        val cont = ca.mediaController
        val playerViewModel = hiltViewModel<RecitationViewModel>(LocalActivity.current as ComponentActivity)
        RecitationArRoute(
            viewModel = playerViewModel,
            navigateBack = { navController.navigateUp() },
            navigateToReadSurah = { surahNumber ->
                navController.navigate(
                    RecitationSelfDetailRoute(surahNumber = surahNumber)
                )
            })
    }
    /* section for translation recitation */
    composable<RecitationTrRoute> {
        val playerViewModel = hiltViewModel<RecitationViewModel>(LocalActivity.current as ComponentActivity)
        RecitationTrRoute(
            viewModel = playerViewModel,
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