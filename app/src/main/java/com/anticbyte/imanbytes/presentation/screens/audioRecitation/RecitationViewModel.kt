package com.anticbyte.imanbytes.presentation.screens.audioRecitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.feature.PlayBackState
import com.anticbyte.imanbytes.feature.QuranAudioController
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
class RecitationViewModel @Inject constructor(
    private val quranRepo: QuranRepo,
    private val mediaController: QuranAudioController
) : ViewModel() {
    private val _recitationUiState =
        MutableStateFlow(RecitationScreenState())

    val recitationUiState: StateFlow<RecitationScreenState> = _recitationUiState.onStart {
        fetchAllSurah()

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _recitationUiState.value
    )

    val mediaControllerState = mediaController.controller.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )
    val currentPlayingSurah = MutableStateFlow(String())
    val isPlaying = mediaController.playBackState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayBackState(isPlaying = false, isPaused = false)
    )

    fun togglePlayPause(surahNumber: String, recitationType: RecitationType) {
        _recitationUiState.update { state ->
            state.copy(nowPlayingSurah = state.surahList.find { it.number == surahNumber })
        }
    }

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = quranRepo.getAllSurah()
            response.fold(
                onSuccess = { surah ->
                    _recitationUiState.update { state ->
                        state.copy(surahList = surah, isLoading = false)
                    }
                    mediaController.createMediaItem(
                        surah = surah,
                        recitationId = RecitationType.TRANSLATION.recitationId
                    )
                },
                onFailure = {
                    _recitationUiState.update { state ->
                        state.copy(errorMessages = it.localizedMessage)
                    }
                })
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaController.releaseFuture()
    }
}