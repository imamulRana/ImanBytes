package com.anticbyte.imanbytes.presentation.screens.onboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPrefsRepo: UserPrefsRepo
) : ViewModel() {
    private val _isOnBoarded = MutableStateFlow(false)
    val isOnBoarded = _isOnBoarded.onStart {
        getIsOnBoarded()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun setIsOnBoarded(isOnBoarded: Boolean) {
        viewModelScope.launch {
            userPrefsRepo.persistNavigationState(isOnBoarded)
            _isOnBoarded.updateAndGet { isOnBoarded }
        }
    }

    fun getIsOnBoarded() {
        viewModelScope.launch {
            userPrefsRepo.retrieveNavigationState()
        }
    }
}