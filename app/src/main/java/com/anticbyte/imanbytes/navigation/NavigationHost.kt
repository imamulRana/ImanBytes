package com.anticbyte.imanbytes.navigation

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
        modifier = modifier,
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