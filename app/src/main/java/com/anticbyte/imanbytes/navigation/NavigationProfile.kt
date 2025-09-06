package com.anticbyte.imanbytes.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileRoute {
    @Serializable
    data object ProfileBaseRoute : ProfileRoute
}
