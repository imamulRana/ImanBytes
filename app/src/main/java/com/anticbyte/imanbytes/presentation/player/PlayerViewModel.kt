package com.anticbyte.imanbytes.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.feature.PlayBackState
import com.anticbyte.imanbytes.feature.PlayerUiState
import com.anticbyte.imanbytes.feature.QuranAudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val audioController: QuranAudioController) :
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

    val mediaUiState = audioController.mediaUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PlayerUiState(
            title = "",
            artist = "",
            mediaId = null,
            mediaIndex = null
        )
    )

    fun onPlay(
        surahList: List<Surah>, recitationId: String,
        surahNumber: String
    ) {
        audioController.playSurah(
            surahList, recitationId,
            surahNumber
        )
    }
}