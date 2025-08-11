package com.anticbyte.imanbytes.domain.repo

import kotlinx.coroutines.flow.Flow

interface UserPrefsRepo {
    suspend fun persistLoginResponse()
    suspend fun retrieveLoginResponse()

    suspend fun persistNavigationState(isNavigationOnBoarded: Boolean)
    suspend fun retrieveNavigationState(): Boolean
}