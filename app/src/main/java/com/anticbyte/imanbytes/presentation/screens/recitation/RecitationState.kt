package com.anticbyte.imanbytes.presentation.screens.recitation

import com.anticbyte.imanbytes.domain.model.Surah

data class RecitationState(
    val surahList: List<Surah> = emptyList(),
    val recitationType: RecitationType = RecitationType.ARABIC,

)