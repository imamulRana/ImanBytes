package com.anticbyte.imanbytes.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anticbyte.imanbytes.presentation.component.AppBottomBar
import com.anticbyte.imanbytes.utils.rememberSnackBarHostState

@Composable
fun NavigationScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val snackBarHostState = rememberSnackBarHostState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentDestination?.hasRoute(Onboarding::class) == false)
                Column {
                   /* SegmentedListItem(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {}, shapes = ListItemDefaults.shapes(),
                        colors = ListItemDefaults.segmentedColors(containerColor = colorScheme.surfaceContainerLow),
                        leadingContent = {
                            Box(modifier = Modifier.size(40.dp)) {
                            }
                        },
                        supportingContent = {
                            Text("surah 1 details")
                        }
                    ) {
                        Text("Surah 1")
                    }*/
                    AppBottomBar(currentRoute = currentDestination, onItemSelected = {
                        navController.navigate(it ?: HomeBaseRoute) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
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
