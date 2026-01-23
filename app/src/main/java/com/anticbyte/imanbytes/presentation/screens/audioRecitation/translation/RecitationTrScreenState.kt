package com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationTrScreenState(
    val isLoading: Boolean = false,
    val surahList: List<Surah> = emptyList(),
    val currentSurahNumber: String = "",
    val errorMessages: String? = null
)


sealed class PlaybackStatus {
    object Playing : PlaybackStatus()
    object Paused : PlaybackStatus()
    object Stopped : PlaybackStatus()
}
