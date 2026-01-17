package com.anticbyte.imanbytes.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.presentation.component.AppBottomBar
import com.anticbyte.imanbytes.utils.rememberSnackBarHostState

@Composable
fun NavigationScaffold(
    navController: NavHostController,
    startDestination: Any,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val snackBarHostState = rememberSnackBarHostState()

    val excludedRoutes = setOf(
        Onboarding::class,
        RandomVerseRoute::class,
        RecitationSelfDetailRoute::class,
        RamadanDetailRoute::class
    )
    Scaffold(
        bottomBar = {
            val shouldShowBottomBar = excludedRoutes.none { exRoute ->
                currentDestination?.hasRoute(exRoute) == true
            }
            if (shouldShowBottomBar)
                AppBottomBar(currentRoute = currentDestination, onItemSelected = {
                    navController.navigate(it ?: HomeBaseRoute) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        },
        snackbarHost = {
            snackBarHostState
        }
    ) {
        NavigationHost(
            navController = navController,
            startDestination = startDestination
        )
    }
}
