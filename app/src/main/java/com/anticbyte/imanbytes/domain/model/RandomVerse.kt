package com.anticbyte.imanbytes.domain.model

data class RandomVerse(
    val number: Int = 0,
    val text: String = "",
    val edition: Edition = Edition(),
    val surah: Surah = Surah(),
    val numberInSurah: Int = 0,
    val juz: Int = 0,
    val manzil: Int = 0,
    val page: Int = 0,
    val ruku: Int = 0,
    val hizbQuarter: Int = 0,
    val sajda: Boolean = false
)
