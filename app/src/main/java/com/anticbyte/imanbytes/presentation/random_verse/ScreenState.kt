package com.anticbyte.imanbytes.presentation.random_verse

import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.Tafsir

data class RandomVerseScreenScreenState(
    val verse: RandomVerse = RandomVerse(),
    val tafsir: Tafsir = Tafsir(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)