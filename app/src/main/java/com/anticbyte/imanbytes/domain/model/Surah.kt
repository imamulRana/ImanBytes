package com.anticbyte.imanbytes.domain.model

data class Surah(
    val surahId: Int? = null,
    val number: String,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: String,
    val revelationType: String
)
