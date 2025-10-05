package com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.RecitationPrefsRepo
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.PlayerSeekType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationPlayBackActions
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
class RecitationArViewModel
@Inject constructor(
    private val recitationRepo: RecitationRepo,
    private val audioManager: QuranAudioManager,
    private val recitationPrefsRepo: RecitationPrefsRepo
) : ViewModel(), RecitationPlayBackActions {
    private val _recitationUiState = MutableStateFlow(RecitationArScreenState(isLoading = true))
    val recitationUiState: StateFlow<RecitationArScreenState> = _recitationUiState
        .onStart {
            fetchAllSurah()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _recitationUiState.value
        )

    val playerState = audioManager.playerStateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PlayerState.PlayerIdle
    )

    val retrieveCurrentSurahNumber = recitationPrefsRepo.retrieveCurrentSurah().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        ""
    )

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = recitationRepo.getAllSurah()
            response.fold(
                onSuccess =
                    { surahs ->
                        _recitationUiState.update { state ->
                            state.copy(
                                surahList = surahs, isLoading = false,
                                nowPlayingSurah = surahs.find { it.number == retrieveCurrentSurahNumber.value })
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
                RecitationType.TRANSLATION
            )
        }
    }

    fun persistCurrentSurahNumber(surahNumber: String?) {
        viewModelScope.launch {
            recitationPrefsRepo.persistCurrentSurah(surahNumber.orEmpty())
        }
    }

    // actions implementation
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
        seekAudio(null, (audioTimeline.value.second * seekTo).roundToLong())
    }

    override fun persistCurrentSurah(surahNumber: String?) {
        persistCurrentSurahNumber(surahNumber)
    }

    override fun onSurahClick(surahNumber: String) {
        _recitationUiState.update { state ->
            state.copy(
                nowPlayingSurah = state.surahList.find { it.number == surahNumber })
        }
    }
}
