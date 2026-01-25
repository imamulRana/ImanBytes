package com.anticbyte.imanbytes.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.serialization.Serializable

@Serializable
data object PrayerTimeRamadanRoute

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        modifier = modifier
            .safeDrawingPadding(),
        navController = navController,
        startDestination = startDestination,
    ) {
        onboardingGraph(navController = navController)
        homeGraph(navController = navController)
        randomVerseGraph(navController)
        knowledgeNavGraph(navController = navController)
        recitationGraph(navController = navController)
    }
}