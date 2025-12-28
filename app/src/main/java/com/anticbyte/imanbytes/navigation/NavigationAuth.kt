package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anticbyte.imanbytes.presentation.auth.AuthScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object AuthBaseRoute

@Serializable
data object AuthRoute

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthBaseRoute>(AuthRoute) {
        composable<AuthRoute> {
            AuthScreenRoute()
        }
    }
}