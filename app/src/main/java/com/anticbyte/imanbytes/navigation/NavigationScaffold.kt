package com.anticbyte.imanbytes.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.presentation.screens.onboard.OnboardingViewModel
import com.anticbyte.imanbytes.utils.rememberSnackBarHostState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NavigationScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,

    ) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val snackBarHostState = rememberSnackBarHostState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel = hiltViewModel<OnboardingViewModel>()
    val isOnboard by viewModel.isOnBoarded.collectAsState()
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
            startDestination = if (isOnboard == true) KnowledgeBaseRoute else Onboarding
        )
    }
}
