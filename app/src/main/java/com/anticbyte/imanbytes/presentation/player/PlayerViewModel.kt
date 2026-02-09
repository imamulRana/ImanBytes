package com.anticbyte.imanbytes.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.feature.MediaPlaybackController
import com.anticbyte.imanbytes.feature.MetadataUiState
import com.anticbyte.imanbytes.feature.PlayBackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val audioController: MediaPlaybackController) :
    ViewModel() {
    val controller = audioController.controller.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    val isPlaying = audioController.playBackState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PlayBackState(isPlaying = false, isPaused = false)
    )

    val metadataUiState = audioController.metadataUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MetadataUiState(
                title = "",
                artist = "",
                mediaId = null
            )
        )

    fun onPlay(
        surahList: List<Surah>, recitationId: String,
        surahNumber: String
    ) {

    }

    fun playSurah(
        recitationId: String,
        surahNumber: String
    ) {
        audioController.playMedia(surahNumber, recitationId)
    }
}