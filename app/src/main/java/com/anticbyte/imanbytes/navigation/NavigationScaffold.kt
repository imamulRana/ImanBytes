package com.anticbyte.imanbytes.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.presentation.screens.onboard.OnboardingViewModel
import com.anticbyte.imanbytes.utils.rememberSnackBarHostState
import kotlin.reflect.KClass

@Composable
fun NavigationScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any,
    ) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val snackBarHostState = rememberSnackBarHostState()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        /*lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.isOnBoarded.collectLatest {
                startDestination = if (it) {
                    KnowledgeBaseRoute
                } else {
                    Onboarding
                }
            }
        }*/
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            /* AppBottomBar(currentRoute = currentDestination, onItemSelected = {
                 navController.navigate(it ?: HomeBaseRoute.HomeRoute) {
                     popUpTo(navController.graph.findStartDestination().id)
                     launchSingleTop = true
                     restoreState = true
                 }
             })*/
        },
        snackbarHost = {
            snackBarHostState
        }
    ) { innerPadding ->
        innerPadding
        NavigationHost(
            modifier = modifier,
            navController,
            startDestination = startDestination
        )
    }
}
