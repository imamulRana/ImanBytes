package com.anticbyte.imanbytes.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.presentation.component.AppBottomBar
import com.anticbyte.imanbytes.presentation.player.MiniPlayer
import com.anticbyte.imanbytes.presentation.player.PlayerViewModel
import com.anticbyte.imanbytes.utils.rememberSnackBarHostState

@Composable
fun NavigationScaffold(
    navController: NavHostController,
    startDestination: Any,
    playerViewModel: PlayerViewModel = hiltViewModel()
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
            if (shouldShowBottomBar) {
                Column {
                    MiniPlayer(
                        viewModel = playerViewModel,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    AppBottomBar(currentRoute = currentDestination, onItemSelected = {
                        navController.navigate(it ?: HomeBaseRoute) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
            }
        },
        snackbarHost = {
            snackBarHostState
        }
    ) {
        it
        NavigationHost(
            navController = navController,
            startDestination = startDestination
        )
    }
}
