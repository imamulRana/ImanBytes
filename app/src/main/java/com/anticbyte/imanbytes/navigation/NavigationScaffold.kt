package com.anticbyte.imanbytes.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    ) { innerPadding -> innerPadding
        NavigationHost(modifier = modifier, navController)
    }
}
