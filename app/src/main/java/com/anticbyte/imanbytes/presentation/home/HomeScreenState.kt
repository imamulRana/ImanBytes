package com.anticbyte.imanbytes.presentation.home

import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.domain.model.RandomVerse

data class HomeScreenState(
    val randomVerse: RandomVerse = RandomVerse(),
    val prayerTime: PrayerTime = PrayerTime(),
    val ramadanOverView: PrayerTime? = null,
    val asma: Asma = Asma(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
