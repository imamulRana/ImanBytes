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
    val player: StateFlow<MediaController?> = mediaController.controller

    val mediaItem: StateFlow<MediaItem> = mediaController.mediaItem
    private val _recitationUiState =
        MutableStateFlow(RecitationScreenState())

    val recitationUiState: StateFlow<RecitationScreenState> = _recitationUiState.onStart {
        fetchAllSurah()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _recitationUiState.value
    )


    /*val playerState = mediaController.playerStateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PlayerState.PlayerIdle
    )*/


    fun setMediaItem(recitationType: RecitationType) {
//        mediaController.setMediaItem(recitationType)
    }

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = quranRepo.getAllSurah()
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

    override fun onCleared() {
        super.onCleared()
        mediaController.releaseFuture()
    }
}