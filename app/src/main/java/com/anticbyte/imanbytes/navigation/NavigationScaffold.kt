package com.anticbyte.imanbytes.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.presentation.component.AppBottomBar
import com.anticbyte.imanbytes.presentation.player.MiniPlayer
import com.anticbyte.imanbytes.presentation.player.PlayerViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationBottomSheet

@Composable
fun NavigationScaffold(
    navController: NavHostController,
    startDestination: Any,
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val player by playerViewModel.controller.collectAsStateWithLifecycle()
    var shouldShowSheet by remember { mutableStateOf(false) }
    val state by playerViewModel.isPlaying.collectAsStateWithLifecycle()

    val excludedRoutes = setOf(
        Onboarding::class,
        RandomVerseRoute::class,
        RecitationSelfDetailRoute::class,
        RamadanDetailRoute::class
    )

    if (shouldShowSheet) RecitationBottomSheet(
        modifier = Modifier,
        showSheet = true,
        onDismiss = { show ->
            shouldShowSheet = show
        },
        onReadSurahClick = { surahNumber -> },
        player = player
    )
    val shouldShowBottomBar = excludedRoutes.none { exRoute ->
        currentDestination?.hasRoute(exRoute) == true
    }
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(currentRoute = currentDestination, onItemSelected = {
                    navController.navigate(it ?: HomeBaseRoute) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                })

            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
            .exclude(WindowInsets.systemBars),
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            NavigationHost(
                modifier = Modifier,
                navController = navController, startDestination = startDestination
            )
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                visible =
                    (state.isPlaying || state.isPaused) && state.currentMediaId != null && shouldShowBottomBar,
                enter = slideInVertically(
                    spring(
                        stiffness = 500f,
                        dampingRatio = 0.5f
                    )
                ),
                exit = fadeOut(
                    animationSpec = spring(
                        stiffness = 500f,
                        dampingRatio = 0.5f
                    )
                ),
                content = {
                    MiniPlayer(
                        playBackState = state, onShowSheet = {
                            shouldShowSheet = true
                        },
                        onDismissSheet = {
                            shouldShowSheet = false
                        },
                        player = player
                    )
                }
            )
        }
    }
}
