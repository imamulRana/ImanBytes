package com.anticbyte.imanbytes.presentation.ramadan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RamadanDayDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RamadanDayDetailScreenState())
    val uiState: StateFlow<RamadanDayDetailScreenState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // TODO: business logic

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
