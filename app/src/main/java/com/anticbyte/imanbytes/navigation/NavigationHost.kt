package com.anticbyte.imanbytes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AuthBaseRoute,
    ) {
        authGraph(navController = navController)
        onboardingGraph(navController = navController)
        knowledgeNavGraph(navController = navController)
        recitationNavGraph(navController = navController)
    }
}