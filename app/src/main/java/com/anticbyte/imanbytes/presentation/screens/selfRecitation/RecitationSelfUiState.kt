package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationSelfUiState(
    val isLoading: Boolean = true,
    val surahList: List<Surah> = emptyList(),
    val errorMessage: String? = null
)
