package com.anticbyte.imanbytes.presentation.home

import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.RandomVerse

data class HomeScreenState(
    val randomVerse: RandomVerse = RandomVerse(),
    val prayerTimes: List<Pair<String, String>> = emptyList(),
    val asma: Asma = Asma(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
