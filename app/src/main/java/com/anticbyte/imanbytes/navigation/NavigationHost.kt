package com.anticbyte.imanbytes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = KnowledgeBaseRoute
    ) {
        knowledgeNavGraph(navController = navController)
        recitationNavGraph(navController = navController)
    }
}