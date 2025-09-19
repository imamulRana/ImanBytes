package com.anticbyte.imanbytes.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.anticbyte.imanbytes.presentation.knowledge.KnowledgeScreenRoute
import com.anticbyte.imanbytes.presentation.knowledge.KnowledgeViewModel
import com.anticbyte.imanbytes.presentation.screens.hadith.HadithScreen
//import com.anticbyte.imanbytes.presentation.screens.recitation.CombineRecitationScreen
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationViewModel
import kotlinx.serialization.Serializable

@Serializable
data object KnowledgeBaseRoute

@Serializable
data object KnowledgeRoute

@Serializable
data object RecitationRoute2

@Serializable
data object HadithRoute


fun NavGraphBuilder.knowledgeNavGraph(
    navController: NavHostController,
) {
    navigation<KnowledgeBaseRoute>(startDestination = KnowledgeRoute) {
        composable<KnowledgeRoute> {
            val viewModel = hiltViewModel<KnowledgeViewModel>()
            KnowledgeScreenRoute(
                modifier = Modifier,
                viewModel = viewModel,
                navigateToQuran = { navController.navigate(RecitationBaseRoute) }
            )
        }
        composable<RecitationRoute2> {
            val viewModel = hiltViewModel<RecitationViewModel>()
            val state by viewModel.recitationUiState.collectAsStateWithLifecycle()
//            CombineRecitationScreen()
        }
        composable<HadithRoute> {
            HadithScreen()
        }
    }
}