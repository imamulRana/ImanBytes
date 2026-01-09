package com.anticbyte.imanbytes.presentation.asma

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.AsmaAlHusnaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsmaHusnaViewModel @Inject constructor(
    private val asmaAlHusnaRepo: AsmaAlHusnaRepo
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(AsmaHusnaScreenState())
    val uiState: StateFlow<AsmaHusnaScreenState> = _uiState.asStateFlow().onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            asmaAlHusnaRepo.getAllAsma().onSuccess {
                _uiState.value = _uiState.value.copy(asmaList = it)
            }.onFailure {
                _uiState.value = _uiState.value.copy(errorMessage = it.message)
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
