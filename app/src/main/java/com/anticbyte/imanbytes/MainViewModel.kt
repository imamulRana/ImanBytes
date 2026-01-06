package com.anticbyte.imanbytes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepo: UserPrefsRepo
) : ViewModel() {
    val isUserOnboarded = userPrefsRepo.retrieveNavigationState().map { it }

    fun setNavigationState(setOnboarding: Boolean) {
        viewModelScope.launch {
            userPrefsRepo.persistNavigationState(setOnboarding)
        }
    }
}
