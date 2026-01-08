package com.anticbyte.imanbytes.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTafsirDto(
    @SerialName("surahName")
    val surahName: String,

    @SerialName("surahNo")
    val surahNo: Int,

    @SerialName("ayahNo")
    val ayahNo: Int,

    @SerialName("tafsirs")
    val tafsirs: List<Tafsir>
) {

    @Serializable
    data class Tafsir(
        @SerialName("author")
        val author: String,

        @SerialName("groupVerse")
        val groupVerse: String? = null, // Only nullable field

        @SerialName("content")
        val content: String
    )
}
