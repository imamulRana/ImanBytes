package com.anticbyte.imanbytes.presentation.home

import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.PrayerTime

data class HomeScreenState(
    val prayerTimes: PrayerTime = PrayerTime(),
    val asma: Asma = Asma(),
    val isLoading: Boolean = false,
    val error: String? = null
)
