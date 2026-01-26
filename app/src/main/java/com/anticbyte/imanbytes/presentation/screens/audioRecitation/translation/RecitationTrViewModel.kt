package com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.feature.PlayBackState
import com.anticbyte.imanbytes.feature.QuranAudioController
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
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
    private val quranRepo: QuranRepo,
    private val mediaController: QuranAudioController
) : ViewModel() {
    private val _recitationUiState = MutableStateFlow(RecitationTrScreenState(isLoading = true))
    val recitationUiState: StateFlow<RecitationTrScreenState> = _recitationUiState
        .onStart {
            fetchAllSurah()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _recitationUiState.value
        )

    val currentPlayingSurah = MutableStateFlow(String())
    val isPlaying = mediaController.playBackState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayBackState(isPlaying = false, isPaused = false)
    )

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = quranRepo.getAllSurah()
            response.fold(
                onSuccess =
                    { surahs ->
                        _recitationUiState.update { state ->
                            state.copy(
                                surahList = surahs, isLoading = false,
                            )
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