package com.anticbyte.imanbytes.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class QuranSearchResponseDto(
    val language: String,
    val query: String,
    val results: List<ResultDto>,
    val total: Int
) {

    @Serializable
    data class ResultDto(
        val surah: SurahDto,
        val verses: List<VerseDto>
    )

    @Serializable
    data class SurahDto(
        val id: Int,
        val name: String,
        val transliteration: String,
        val translation: String
    )

    @Serializable
    data class VerseDto(
        val id: Int,
        val text: String,
        val translation: String
    )
}