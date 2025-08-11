package com.anticbyte.imanbytes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.anticbyte.imanbytes.presentation.home.HomeScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AuthBaseRoute>(startDestination = AuthBaseRoute.OnboardingRoute) {
        composable<AuthBaseRoute.LoginRoute> { }
        composable<AuthBaseRoute.SignupRoute> { }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation<HomeBaseRoute>(startDestination = HomeBaseRoute.HomeRoute) {
        composable<HomeBaseRoute.HomeRoute> {
            HomeScreen()
        }
    }
}

fun NavGraphBuilder.knowledgeGraph(navController: NavController) {
    navigation<KnowledgeBaseRoute>(startDestination = KnowledgeBaseRoute.KnowledgeRoute) {
        composable<KnowledgeBaseRoute.QuranRoute> {}
        composable<KnowledgeBaseRoute.HadithRoute> {}
    }

}

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation<ProfileBaseRoute>(startDestination = ProfileBaseRoute.ProfileRoute) {
        composable<ProfileBaseRoute.ProfileRoute> { }
    }
}