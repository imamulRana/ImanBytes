package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.presentation.screens.recitation.component.PlayerSeekType
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
    private val recitationRepo: RecitationRepo,
    private val quranAudioManager: QuranAudioManager
) : ViewModel() {
    private val _recitationUiState =
        MutableStateFlow(RecitationState())

    /*    val StateFlow<ScreenUiState<RecitationState>>.recitationData: RecitationState
            get() = (value as ScreenUiState.Success).data*/
    val recitationUiState: StateFlow<RecitationState> = _recitationUiState.onStart {
        getAllSurah()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RecitationState()
    )

    val recitationTimeline = quranAudioManager.audioTimeline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Pair(0L, 0L)
    )

    fun getAllSurah() {
        viewModelScope.launch {
            recitationRepo.getAllSurah().fold(onSuccess = { surahs ->
                _recitationUiState.update { screenUiState ->
                    screenUiState.copy(surahList = surahs)
                }
            }, onFailure = {
            })
        }
    }

    val isPlaying = quranAudioManager.playerStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
    val mediaItem = quranAudioManager.audioTrackFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val currentProgress = quranAudioManager.currentProgress.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0f
    )

    fun playSurah(surahNumber: String, recitationType: RecitationType) {
        viewModelScope.launch {
            _recitationUiState.update { screenUiState ->
                screenUiState.copy(
                    recitationType = recitationType,
                    nowPlayingSurah = screenUiState.surahList.find { it.number == surahNumber }
                )
            }
            quranAudioManager.playOrToggle(surahNumber, recitationType)
        }
    }

    fun seekAudio(seekType: PlayerSeekType) {
        viewModelScope.launch {
            quranAudioManager.seekAudio(seekType)
        }
    }

}