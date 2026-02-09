package com.anticbyte.imanbytes.presentation.ramadan

import com.anticbyte.imanbytes.domain.model.RamadanCalender

data class RamadanDayDetailState(
    val day: RamadanCalender? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)