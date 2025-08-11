package com.anticbyte.imanbytes.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.utils.rememberSnackBarHostState

@Composable
fun NavigationScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentRoute: String? = currentDestination?.route?.substringAfterLast('.')
    val snackBarHostState = rememberSnackBarHostState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        /*
                bottomBar = {
                    if (currentRoute != Topic::class.simpleName
                        && currentRoute != FlashCard::class.simpleName
                        && currentRoute != Onboarding::class.simpleName
                        && currentRoute != Login::class.simpleName
                    ) {
                        AppBottomBar(
                            modifier = modifier,
                            currentRoute = currentRoute,
                            onItemSelected = {
                                navController.navigate(it ?: Home) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                },
        */
        snackbarHost = {
            snackBarHostState
        }
    ) { innerPadding ->
//        AppNavigation(modifier.padding(innerPadding), navBackStack)
        NavigationHost(modifier = modifier.padding(innerPadding), navController)
    }
}
