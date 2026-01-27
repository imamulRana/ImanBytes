package com.anticbyte.imanbytes.domain.repo

import kotlinx.coroutines.flow.Flow

interface UserPrefsRepo {
    suspend fun persistNavigationState(onBoarded: Boolean)
    fun retrieveNavigationState(): Flow<Boolean>
}