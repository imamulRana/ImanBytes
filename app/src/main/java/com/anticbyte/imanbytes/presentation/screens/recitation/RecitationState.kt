package com.anticbyte.imanbytes.presentation.screens.recitation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationState(
    val isLoading: Boolean = false,
    val recitationType: RecitationType = RecitationType.ARABIC,
    val surahList: List<Surah> = emptyList(),
    val nowPlayingSurah: Surah? = null,
    val errorMessages: String? = null
)
/*

sealed interface RecitationScreenState{
    object Loading: RecitationScreenState
    data class Error(val message: String): RecitationScreenState
    data class Success(val data: RecitationState): RecitationScreenState
}*/
