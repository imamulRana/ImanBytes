package com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.PlayerSeekType
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
    private val audioManager: QuranAudioManager
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
    val playerState = audioManager.playerStateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PlayerState.PlayerIdle
    )
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

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = quranRepo.getAllSurah()
            response.fold(
                onSuccess =
                    { surahs ->
                        _recitationUiState.update { state ->
                            state.copy(
                                surahList = surahs, isLoading = false,
//                                nowPlayingSurah = surahs.find { it.number == retrieveCurrentSurahNumber.value }
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

    private fun seekAudio(
        seekType: PlayerSeekType?,
        seekToPosition: Long
    ) {
        viewModelScope.launch {
            audioManager.seekAudio(seekType, seekToPosition)
        }
    }

    private fun playSurah(surahNumber: String) {
        viewModelScope.launch {
            audioManager.playOrToggle(
                surahNumber,
                RecitationType.TRANSLATION
            )
        }
    }

    private fun persistCurrentSurahNumber(surahNumber: String?) {
        viewModelScope.launch {
        }
    }

}