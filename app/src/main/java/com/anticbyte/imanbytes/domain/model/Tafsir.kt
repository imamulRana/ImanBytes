package com.anticbyte.imanbytes.domain.model

data class Tafsir(
    val surahName: String = "",
    val surahNo: Int = 0,
    val ayahNo: Int = 0,
    val tafsirs: List<TafsirData> = emptyList()
) {
    data class TafsirData(
        val author: String = "",
        val groupVerse: String? = null,   // nullable by API contract
        val content: String = ""
    )
}

