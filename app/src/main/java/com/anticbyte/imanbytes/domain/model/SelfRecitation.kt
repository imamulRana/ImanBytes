package com.anticbyte.imanbytes.domain.model

data class SelfRecitation(
    val name: String = "",
    val numberInQuran: String = "",
    val englishName: String = "",
    val englishNameTranslation: String = "",
    val numberOfAyahs: String = "",
    val revelationType: String = "",
    val edition: String = "",
    val ayahs: List<Ayah> = emptyList()
) {
    data class Ayah(
        val number: Int = 0,
        val numberInSurah: Int = 0,
        val text: String = "",
        val sajda: Boolean = false
    )
}
