package com.anticbyte.imanbytes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Surah(
    val number: String = "",
    val name: String = "",
    val englishName: String = "",
    val englishNameTranslation: String = "",
    val numberOfAyahs: String = "",
    val revelationType: String = "",
)
