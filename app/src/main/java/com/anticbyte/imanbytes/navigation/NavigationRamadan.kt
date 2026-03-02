package com.anticbyte.imanbytes.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.savedstate.SavedState
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.presentation.ramadan.RamadanCalendarRoute
import com.anticbyte.imanbytes.presentation.ramadan.RamadanCalendarViewModel
import com.anticbyte.imanbytes.presentation.ramadan.RamadanDayDetailRoute
import com.anticbyte.imanbytes.presentation.ramadan.RamadanDayDetailViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data object RamadanBaseRoute

@Serializable
data object RamadanRoute

val RamadanCalendarNavType: NavType<RamadanCalender> =
    object : NavType<RamadanCalender>(isNullableAllowed = false) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: RamadanCalender
        ) {
            TODO("Not yet implemented")
        }

        override fun get(
            bundle: SavedState,
            key: String
        ): RamadanCalender {
            TODO("Not yet implemented")
        }

        override fun parseValue(value: String): RamadanCalender {
            TODO("Not yet implemented")
        }

    }

@Serializable
data class RamadanDetailRoute(val ramadanCalender: String)

@Serializable
data object SearchRoute
fun NavGraphBuilder.ramadanGraph(navController: NavHostController) {
    navigation<RamadanBaseRoute>(startDestination = RamadanRoute) {
        composable<RamadanRoute> {
            val viewModel = hiltViewModel<RamadanCalendarViewModel>()
            RamadanCalendarRoute(
                viewModel = viewModel,
                navigateUp = { navController.navigateUp() },
                navigateToDetail = {
                    val encode = Json.encodeToString(it)
                    navController.navigate(RamadanDetailRoute(encode))
                })
        }
        composable<RamadanDetailRoute>(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        ) {
            val viewModel = hiltViewModel<RamadanDayDetailViewModel>()
            RamadanDayDetailRoute(
                viewModel = viewModel,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}