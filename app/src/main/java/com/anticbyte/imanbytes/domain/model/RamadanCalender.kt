package com.anticbyte.imanbytes.domain.model

data class RamadanCalender(
    val gregorianDate: String = "",
    val hijriDate: String = "",
    val holidays: List<String> = emptyList(),
    val prayerTimes: List<Pair<String, String>> = emptyList()
)
