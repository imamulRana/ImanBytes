package com.anticbyte.imanbytes.presentation.screens.recitation.arabic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.recitation.component.PlayerSeekType
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationPlayBackActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
class RecitationArViewModel @Inject constructor(
    private val recitationRepo: RecitationRepo,
    private val audioManager: QuranAudioManager
) : ViewModel(), RecitationPlayBackActions {
    private val _recitationUiState = MutableStateFlow(RecitationArScreenState(isLoading = true))
    val recitationUiState: StateFlow<RecitationArScreenState> = _recitationUiState
        .onStart {
            fetchAllSurah()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RecitationArScreenState(isLoading = true)
        )

    val playerState = audioManager.playerStateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PlayerState.PlayerIdle
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

    fun seekAudio(
        seekType: PlayerSeekType?,
        seekToPosition: Long
    ) {
        viewModelScope.launch {
            audioManager.seekAudio(seekType, seekToPosition)
        }
    }

    fun seekAudioP(
        seekToPosition: Long
    ) {
        viewModelScope.launch {
            audioManager.seekAudioP(seekToPosition)
        }
    }

    val audioTimeline = audioManager.audioTimeline.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        Pair(0L, 0L)
    )

    val currentProgress = audioManager.currentProgress.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        0f
    )

    fun playSurah(surahNumber: String) {
        viewModelScope.launch {
            audioManager.playOrToggle(
                surahNumber,
                RecitationType.ARABIC
            )
        }
    }

    override fun onPlayPause(surahNumber: String) {
        _recitationUiState.update { state ->
            state.copy(
                nowPlayingSurah = state.surahList.find { it.number == surahNumber })
        }
        playSurah(surahNumber)
    }

    override fun onSeekForward() {
        seekAudio(PlayerSeekType.FORWARD, 10000L)
    }

    override fun onSeekBackward() {
        seekAudio(PlayerSeekType.BACKWARD, 10000L)
    }

    override fun onSeek(seekTo: Float) {
        seekAudioP((audioTimeline.value.second * seekTo).roundToLong())
    }
}
