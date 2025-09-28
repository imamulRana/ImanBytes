package com.anticbyte.imanbytes.presentation.screens.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPrefsRepo: UserPrefsRepo
) : ViewModel() {
    private val _isOnBoarded = userPrefsRepo.retrieveNavigationState()
    val isOnBoarded = _isOnBoarded.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = null
    )

    fun setIsOnBoarded(isOnBoarded: Boolean) {
        viewModelScope.launch {
            userPrefsRepo.persistNavigationState(isOnBoarded)
        }
    }
}