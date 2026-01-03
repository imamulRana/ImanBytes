package com.anticbyte.imanbytes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: PrayerTimeRepo) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPrayerTime()
    }
    private fun fetchPrayerTime() {
        viewModelScope.launch {
            repo.getPrayerTimes("01-01-2026").onSuccess { response ->
                _uiState.update {
                    it.copy(prayerTimes = response)
                }
            }.onFailure {
                _uiState.value = _uiState.value.copy(error = it.localizedMessage)
            }
        }
    }
}