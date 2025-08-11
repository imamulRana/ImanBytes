package com.anticbyte.imanbytes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepo: UserPrefsRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow<Boolean?>(null)
    val uiState = _uiState.onStart {
        getIsOnBoarded()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun setNavigationState(state: Boolean) {
        viewModelScope.launch {
            userPrefsRepo.persistNavigationState(state)
            _uiState.value = state
        }
    }

    fun getIsOnBoarded() {
        viewModelScope.launch {
            userPrefsRepo.retrieveNavigationState()
        }
    }

}
