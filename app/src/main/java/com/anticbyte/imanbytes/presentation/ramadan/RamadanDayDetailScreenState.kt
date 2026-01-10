package com.anticbyte.imanbytes.presentation.ramadan

import com.anticbyte.imanbytes.domain.model.RamadanCalender

data class RamadanDayDetailScreenState(
    val monthPrayerTime: RamadanCalender = RamadanCalender(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)