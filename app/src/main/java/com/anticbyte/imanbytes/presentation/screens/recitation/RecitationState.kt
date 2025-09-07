package com.anticbyte.imanbytes.presentation.screens.recitation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationState(
    val isLoading: Boolean = false,
    val recitationType: RecitationType = RecitationType.ARABIC,
    val surahList: List<Surah> = emptyList(),
    val nowPlayingSurah: Surah? = null,
    val errorMessages: String? = null
)