package com.anticbyte.imanbytes.feature.quran_search

import com.anticbyte.imanbytes.domain.model.QuranSearch

data class QuranSearchUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchItems : QuranSearch? = null
)
