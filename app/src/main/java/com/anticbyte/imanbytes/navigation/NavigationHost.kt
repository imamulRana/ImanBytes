package com.anticbyte.imanbytes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anticbyte.imanbytes.presentation.profile.ProfileScreen
import com.anticbyte.imanbytes.presentation.ramadan.RamadanDayDetailRoute
import kotlinx.serialization.Serializable

@Serializable data object PrayerTimeRamadanRoute
@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        onboardingGraph(navController = navController)
        homeGraph(navController = navController)
        randomVerseGraph(navController)
        knowledgeNavGraph(navController = navController)
        composable<PrayerTimeRamadanRoute> {
            RamadanDayDetailRoute()
        }
    }
}