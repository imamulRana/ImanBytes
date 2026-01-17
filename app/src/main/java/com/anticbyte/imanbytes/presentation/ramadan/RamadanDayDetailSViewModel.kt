package com.anticbyte.imanbytes.presentation.ramadan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.navigation.RamadanDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class RamadanDayDetailSViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val args = savedStateHandle.toRoute<RamadanDetailRoute>()
    private val ramadanCalendarObject: RamadanCalender = Json.decodeFromString(args.ramadanCalender)
    private val _uiState = MutableStateFlow(RamadanDayDetailSScreenState())
    val uiState: StateFlow<RamadanDayDetailSScreenState> = _uiState.asStateFlow().onStart {
//        _uiState.value.copy(day = args.)
    }.stateIn(
        viewModelScope,
        initialValue = _uiState.value,
        started = SharingStarted.WhileSubscribed(5000)
    )

    init {
        // 3. Initialize the state with the decoded object.
        _uiState.update { it.copy(day = ramadanCalendarObject) }
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
