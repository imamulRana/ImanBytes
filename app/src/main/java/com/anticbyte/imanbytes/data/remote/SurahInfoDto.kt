package com.anticbyte.imanbytes.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurahInfoDto(
    @SerialName("chapter_info") val chapterInfo: ChapterInfo
) {
    @Serializable
    data class ChapterInfo(
        @SerialName("id") val id: Int,
        @SerialName("chapter_id") val chapterId: Int,
        @SerialName("language_name") val languageName: String,
        @SerialName("short_text") val shortText: String,
        @SerialName("source") val source: String,
        @SerialName("text") val text: String
    )
}