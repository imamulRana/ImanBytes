package com.anticbyte.imanbytes.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.anticbyte.imanbytes.presentation.knowledge.KnowledgeScreenRoot
import com.anticbyte.imanbytes.presentation.knowledge.KnowledgeViewModel
import com.anticbyte.imanbytes.presentation.screens.hadith.HadithScreen
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationScreen
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationViewModel
import kotlinx.serialization.Serializable

@Serializable
data object KnowledgeBaseRoute

@Serializable
data object KnowledgeRoute

@Serializable
data object RecitationRoute

@Serializable
data object HadithRoute


fun NavGraphBuilder.knowledgeNavGraph(navController: NavHostController) {
    navigation<KnowledgeRoute>(startDestination = KnowledgeBaseRoute) {
        composable<KnowledgeBaseRoute> {
            val viewModel = hiltViewModel<KnowledgeViewModel>()
            KnowledgeScreenRoot(
                modifier = Modifier,
                viewModel = viewModel,
                navigateToQuran = { navController.navigate(RecitationRoute) }
            )
        }
        composable<RecitationRoute> {
            val viewModel = hiltViewModel<RecitationViewModel>()
            val state by viewModel.recitationUiState.collectAsStateWithLifecycle()
            RecitationScreen()
        }
        composable<HadithRoute> {
            HadithScreen()
        }
    }
}