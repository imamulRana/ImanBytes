package com.anticbyte.imanbytes.presentation.ramadan

import com.anticbyte.imanbytes.domain.model.RamadanCalender

data class RamadanDayDetailScreenState(
    val monthPrayerTime: List<RamadanCalender> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)