package com.anticbyte.imanbytes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.profile.ProfileScreen
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        onboardingGraph(navController = navController)
        homeGraph()
        knowledgeNavGraph(navController = navController)
    }
}