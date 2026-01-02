package com.anticbyte.imanbytes.domain.model

data class PrayerTime(
    val fajr: String = "",
    val dhuhr: String = "",
    val asr: String = "",
    val maghrib: String = "",
    val isha: String = "",
    val sunRise: String = "",
    val sunSet: String = "",
    val midNight: String = ""
)