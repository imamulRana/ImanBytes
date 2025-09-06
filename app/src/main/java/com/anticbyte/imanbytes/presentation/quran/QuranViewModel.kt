package com.anticbyte.imanbytes.presentation.quran

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.feature.NetworkManager
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.utils.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val quranRepo: QuranRepo,
    private val audioManager: QuranAudioManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _quranState =
        MutableStateFlow<ScreenUiState<Quran>>(ScreenUiState.Loading)
    val quranState = _quranState.onStart {
        fetchNewVerse()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ScreenUiState.Loading
        )


    fun fetchNewVerse() {

        viewModelScope.launch {
            val result: Result<Quran> = quranRepo.getQuranData()

            result.fold(
                onSuccess = { response ->
                    _quranState.update {
                        ScreenUiState.Success(data = response)
                    }
                },
                onFailure = { throwable ->
                    _quranState.update {
                        ScreenUiState.Error(message = throwable.message ?: "Something went wrong")
                    }
                }
            )
        }
    }

    val isPlaying = audioManager.isPlayingFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = false
    )

    fun playQuranAudio(audioUrl: String) {
        viewModelScope.launch {
            audioManager.playQuranAudio(audioUrl)
        }
    }

    fun pauseQuranAudio() {
        viewModelScope.launch {
            audioManager.pauseQuranAudio()
        }
    }

    val currentProgress = audioManager.currentProgress.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0f
    )

    val networkManager = NetworkManager(context)
    val networkStatus = networkManager.network.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = ""
    )

    var navigationIcon by mutableStateOf<@Composable RowScope.() -> Unit>(
        {},
        referentialEqualityPolicy()
    )


    override fun onCleared() {
        super.onCleared()
        audioManager.releasePlayer()
    }
}