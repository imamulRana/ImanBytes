package com.anticbyte.imanbytes.presentation.screens.audioRecitation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationScreenState(
    val isLoading: Boolean = false,
    val recitationType: RecitationType = RecitationType.TRANSLATION,
    val surahList: List<Surah> = emptyList(),
    val nowPlayingSurah: Surah? = null,
    val errorMessages: String? = null
)

sealed class PlayerState {
    data object PlayerIdle : PlayerState()
    data object PlayerLoading : PlayerState()
    data object PlayerPaused : PlayerState()
    data object PlayerPlaying : PlayerState()
}