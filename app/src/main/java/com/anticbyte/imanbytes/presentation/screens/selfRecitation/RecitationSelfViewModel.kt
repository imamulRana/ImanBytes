package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecitationSelfViewModel @Inject constructor(private val recitationRepo: RecitationRepo) : ViewModel() {
    private val _recitationSelfUiState = MutableStateFlow(RecitationSelfUiState())
    val selfRecitationUiState = _recitationSelfUiState
        .onStart {
            fetchAllSurah()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _recitationSelfUiState.value
        )

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = recitationRepo.getAllSurah()
            response.fold(
                onSuccess = { surahs ->
                    _recitationSelfUiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            surahList = surahs
                        )
                    }
                },
                onFailure = {
                    _recitationSelfUiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            errorMessage = it.localizedMessage
                        )
                    }
                }
            )
        }
    }
}