package com.anticbyte.imanbytes.presentation.ramadan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RamadanCalendarViewModel @Inject constructor(
    private val prayerTimeRepo: PrayerTimeRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(RamadanCalendarState())
    val uiState: StateFlow<RamadanCalendarState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            prayerTimeRepo.ramadanPrayerTimesGet().onSuccess { response ->
                _uiState.update { lState ->
                    lState.copy(
                        monthPrayerTime = response,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }.onFailure {
                _uiState.update { lState ->
                    lState.copy(
                        errorMessage = it.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}
