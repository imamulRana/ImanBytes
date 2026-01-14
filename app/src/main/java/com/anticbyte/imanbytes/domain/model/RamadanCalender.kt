package com.anticbyte.imanbytes.domain.model

data class RamadanCalender(
    val hijriDay: String = "",
    val gregorianWeekday: String = "",
    val gregorianDate: String = "",
    val hijriDate: String = "",
    val imsak: String = "",
    val sunset: String = "",
    val holidays: List<String> = emptyList(),
    val prayerTimes: List<Pair<String, String>> = emptyList()
)
