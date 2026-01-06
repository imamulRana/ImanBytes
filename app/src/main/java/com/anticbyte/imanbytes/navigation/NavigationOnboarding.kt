package com.anticbyte.imanbytes.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anticbyte.imanbytes.presentation.screens.onboard.OnboardingScreen
import com.anticbyte.imanbytes.presentation.screens.onboard.OnboardingViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Onboarding

fun NavGraphBuilder.onboardingGraph(navController: NavHostController) {
    composable<Onboarding> {
        val viewModel = hiltViewModel<OnboardingViewModel>()
        OnboardingScreen(onContinue = {
            viewModel.setIsOnBoarded(true)
            navController.navigate(KnowledgeBaseRoute)
        })
    }
}