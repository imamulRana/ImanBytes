package com.anticbyte.imanbytes.presentation.screens.recitation.translation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecitationTrViewModel @Inject constructor(
    private val recitationRepo: RecitationRepo,
    private val audioManager: QuranAudioManager
) : ViewModel() {
    private val _recitationUiState = MutableStateFlow(RecitationTrScreenState(isLoading = true))
    val recitationUiState: StateFlow<RecitationTrScreenState> = _recitationUiState
        .onStart {
            fetchAllSurah()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RecitationTrScreenState(isLoading = true)
        )

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = recitationRepo.getAllSurah()
            response.fold(
                onSuccess =
                    { surahs ->
                        _recitationUiState.update { state ->
                            state.copy(surahList = surahs, isLoading = false)
                        }
                    },
                onFailure = {
                    _recitationUiState.update { state ->
                        state.copy(errorMessages = it.localizedMessage)
                    }
                })
        }
    }
}