package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.utils.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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
    private val _recitationState =
        MutableStateFlow<RecitationState>(RecitationState())
    val recitationUiState: StateFlow<ScreenUiState<RecitationState>> = _recitationState.map {
        ScreenUiState.Success(it)
    }.onStart {
        getAllSurah()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScreenUiState.Loading
    )

    val recitationState: StateFlow<RecitationState> = _recitationState.asStateFlow()

    fun getAllSurah() {
        viewModelScope.launch {
            _recitationState.emit(RecitationState())
            recitationRepo.getAllSurah()
                .fold(
                    onSuccess = { surahs ->
                        _recitationState.update {
                            it.copy(surahList = surahs)
                        }
                    },
                    onFailure = {}
                )
        }
    }

    fun playSurah(surahId: Int) {
        viewModelScope.launch {
            quranAudioManager.playQuranAudio("https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/$surahId.mp3")
        }
    }

    fun pauseSurah() {
        viewModelScope.launch {
            quranAudioManager.pauseQuranAudio()
        }
    }
}