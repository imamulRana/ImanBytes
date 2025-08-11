package com.anticbyte.imanbytes.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userPrefsRepo: UserPrefsRepo) : ViewModel() {

    private val _deviceID = MutableStateFlow("")
    val deviceID = _deviceID.asStateFlow()

    private val _isOnboarded = MutableStateFlow<Boolean>(false)
    val isOnboarded = _isOnboarded.onStart {
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false,
    )

    val isOnboardingDone = false




    fun setUserOnboarding() {
        viewModelScope.launch {
            userPrefsRepo.persistNavigationState(true)
        }
    }
}