package com.anticbyte.imanbytes.domain.model

data class PrayerTime(
    val hijriDate: String = "",
    val readableDate: String = "",
    val gregorianDate: String = "",
    val suhoor: String = "",
    val iftaar: String = "",
    val prayerTime: List<Pair<String, String>> = emptyList()
)