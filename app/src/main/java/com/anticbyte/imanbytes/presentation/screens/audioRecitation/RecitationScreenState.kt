package com.anticbyte.imanbytes.presentation.screens.audioRecitation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationScreenState(
    val isLoading: Boolean = false,
    val recitationType: RecitationType = RecitationType.TRANSLATION,
    val surahList: List<Surah> = emptyList(),
    val nowPlayingSurah: Surah? = null,
    val playerState: PlayerState = PlayerState.PlayerIdle,
    val audioProgress: Float = 0f,
    val errorMessages: String? = null
)
/*

sealed interface RecitationScreenState{
    object Loading: RecitationScreenState
    data class Error(val message: String): RecitationScreenState
    data class Success(val data: RecitationState): RecitationScreenState
}*/

sealed class PlayerState {
    data object PlayerIdle : PlayerState()
    data object PlayerLoading : PlayerState()
    data object PlayerPaused : PlayerState()
    data object PlayerPlaying : PlayerState()
}