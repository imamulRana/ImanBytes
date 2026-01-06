package com.anticbyte.imanbytes.domain.repo

import kotlinx.coroutines.flow.Flow

interface UserPrefsRepo {
    suspend fun persistNavigationState(isNavigationOnBoarded: Boolean)
    fun retrieveNavigationState(): Flow<Boolean>
}