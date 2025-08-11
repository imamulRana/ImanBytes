package com.anticbyte.imanbytes.navigation

import kotlinx.serialization.Serializable

// Auth Section routes
@Serializable
data object AuthBaseRoute {
    @Serializable data object LoginRoute

    @Serializable data object SignupRoute

    @Serializable data object OnboardingRoute
}

// Home Section routes
@Serializable
data object HomeBaseRoute {
    @Serializable data object HomeRoute
}

//Knowledge Section routes
@Serializable
data object KnowledgeBaseRoute {
    @Serializable data object KnowledgeRoute

    @Serializable data object QuranRoute

    @Serializable data object HadithRoute
}

// Profile Section routes
@Serializable data object ProfileBaseRoute {
    @Serializable data object ProfileRoute
}
