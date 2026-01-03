package com.anticbyte.imanbytes.presentation.screens.audioRecitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.anticbyte.imanbytes.domain.repo.QuranRepo
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
    val currentPlayingSurah = mediaController.currentPlayingSurah.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
    val isPlaying = mediaController.isPlaying.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun togglePlayPause(surahNumber: String) {
        mediaController.togglePlayPause(surahNumber)
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
                        recitationUiState.value.recitationType.recitationId
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