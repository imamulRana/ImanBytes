package com.anticbyte.imanbytes.presentation.home

import com.anticbyte.imanbytes.domain.model.PrayerTime

data class HomeScreenState(
    val prayerTimes: PrayerTime = PrayerTime(),
    val isLoading: Boolean = false,
    val error: String? = null
)
