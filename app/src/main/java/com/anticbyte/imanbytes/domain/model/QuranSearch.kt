package com.anticbyte.imanbytes.domain.model

data class QuranSearch(
    val language: String,
    val query: String,
    val results: List<Pair<Surah, List<Verse>>>,
    val total: Int
) {
    data class Surah(
        val id: Int,
        val name: String,
        val transliteration: String,
        val translation: String
    )

    data class Verse(
        val id: Int,
        val text: String,
        val translation: String
    )
}
