package com.anticbyte.imanbytes.domain.model

data class RamadanCalender(
    val date: String = "",
    val day: String = "",
    val designation: String = "",
    val hijri: String = "",
    val gregorian: String = "",
    val meta: String = "",
    val method: String = "",
    val month: String = "",
    val year: String = "",
    val holidays: List<String> = emptyList(),
    val prayerTimes: List<Pair<String, String>> = emptyList(),
    /*val fajr: String = "",
    val dhuhr: String = "",
    val asr: String = "",
    val maghrib: String = "",
    val isha: String = "",
    val imsak: String = "",
    val sunrise: String = "",
    val sunset: String = "",*/
    val midnight: String = "",
)
