package com.anticbyte.imanbytes.presentation.screens.audioRecitation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.repo.RecitationPrefsRepo
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.PlayerSeekType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationPlaybackAction
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
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
class RecitationViewModel @Inject constructor(
    private val recitationRepo: RecitationRepo,
    private val audioManager: QuranAudioManager,
    private val recitationPrefsRepo: RecitationPrefsRepo
) : ViewModel() {
    val remoteConfig = Firebase.remoteConfig
    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 0
    }
    private val _recitationUiState =
        MutableStateFlow(RecitationScreenState())

    init {

        viewModelScope.launch {
            /*try {
                remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults).await()
                remoteConfig.setConfigSettingsAsync(configSettings).await()

                val updated = remoteConfig.fetchAndActivate().await()
                Log.d("RemoteConfig", "Config params updated: $updated")

                val friday = remoteConfig.getBoolean("friday_special")
                val values = remoteConfig.getValue("friday_special")
                _recitationUiState.update { state ->
                    state.copy(sectionColor = friday)
                }
                Log.d("FridayTag", "${values.source}")
                Log.d("FridayTag", "$friday")
            } catch (e: Exception) {
                Log.d("RemoteConfig", "Config params update failed: ${e.message}")
            }*/
            remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("RemoteConfig", "Config params updated: ${it.isSuccessful}")
                    val friday = remoteConfig.getBoolean("friday_special")
                    val values = remoteConfig.getValue("friday_special")
                    Log.d("FridayTag", "${values.source}")
                    Log.d("FridayTag", "$friday")
                    _recitationUiState.update { state ->
                        state.copy(sectionColor = friday)
                    }
                }
            }
        }
    }

    val recitationUiState: StateFlow<RecitationScreenState> = _recitationUiState.onStart {
        fetchAllSurah()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _recitationUiState.value
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
            val response = recitationRepo.getAllSurah()
            response.fold(
                onSuccess =
                    { surahs ->
                        _recitationUiState.update { state ->
                            state.copy(
                                surahList = surahs, isLoading = false,
                                nowPlayingSurah = surahs.find {
                                    it.number.plus(
                                        recitationUiState.value.recitationType
                                    ) == retrieveCurrentSurahNumber.value.plus(
                                        recitationUiState.value.recitationType
                                    )
                                })
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

    fun playSurah(surahNumber: String, recitationType: RecitationType) {
        viewModelScope.launch {
            audioManager.playOrToggle(
                surahNumber,
                recitationType
            )
        }
    }

    fun persistCurrentSurahNumber(surahNumber: String?) {
        viewModelScope.launch {
            recitationPrefsRepo.persistCurrentSurah(
                surahNumber?.plus(
                    recitationUiState.value.recitationType
                ).orEmpty()
            )
        }
    }

    val playerActions = RecitationPlaybackAction(
        playPause = { surahNumber ->
            _recitationUiState.update { state ->
                state.copy(
                    nowPlayingSurah = state.surahList.find { it.number == surahNumber })
            }
            playSurah(
                surahNumber,
                recitationUiState.value.recitationType
            )
        },
        seekForward = {
            seekAudio(
                PlayerSeekType.FORWARD,
                10000L
            )
        },
        seekBackward = {
            seekAudio(
                PlayerSeekType.BACKWARD,
                10000L
            )
        },
        seek = { seekTo ->
            seekAudio(null, (audioTimeline.value.second * seekTo).roundToLong())
        },
        persistCurrentSurah = { surahNumber ->
            persistCurrentSurahNumber(surahNumber)
        },
    )
}