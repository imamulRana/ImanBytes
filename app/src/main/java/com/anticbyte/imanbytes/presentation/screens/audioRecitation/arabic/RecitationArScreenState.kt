package com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic

import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState

data class RecitationArScreenState(
    val isLoading: Boolean = false,
    val surahList: List<Surah> = emptyList(),
    val errorMessages: String? = null
)
