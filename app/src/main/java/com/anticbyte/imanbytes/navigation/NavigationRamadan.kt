package com.anticbyte.imanbytes.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.ramadan.RamadanCalendarRoute
import com.anticbyte.imanbytes.presentation.ramadan.RamadanDayDetailViewModel
import kotlinx.serialization.Serializable

@Serializable
data object RamadanBaseRoute

@Serializable
data object RamadanRoute

fun NavGraphBuilder.ramadanGraph(navController: NavHostController) {
    navigation<RamadanBaseRoute>(startDestination = RamadanRoute) {
        composable<RamadanRoute> {
            val viewModel = hiltViewModel<RamadanDayDetailViewModel>()
            RamadanCalendarRoute(
                viewModel = viewModel,
                navigateUp = { navController.navigateUp() })
        }
    }
}