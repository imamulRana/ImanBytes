package com.anticbyte.imanbytes.presentation.screens.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPrefsRepo: UserPrefsRepo
) : ViewModel() {
    val isOnBoarded = userPrefsRepo.retrieveNavigationState()

    fun setIsOnBoarded(isOnBoarded: Boolean) {
        viewModelScope.launch {
            userPrefsRepo.persistNavigationState(isOnBoarded)
        }
    }
}