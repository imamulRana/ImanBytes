package com.anticbyte.imanbytes.presentation.screens.recitation.arabic

import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState

data class RecitationArScreenState(
    val isLoading: Boolean = false,
    val surahList: List<Surah> = emptyList(),
    val nowPlayingSurah: Surah? = null,
    val playerState: PlayerState = PlayerState.PlayerIdle,
    val audioProgress: Float = 0f,
    val errorMessages: String? = null
)
