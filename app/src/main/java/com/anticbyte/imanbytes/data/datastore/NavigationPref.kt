package com.anticbyte.imanbytes.data.datastore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NavigationPref(
    @SerialName("is_onboarded")
    val isOnBoarded: Boolean? = null,
    @SerialName("is_logged_in")
    val isLoggedIn: Boolean? = null,
)