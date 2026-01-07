package com.anticbyte.imanbytes.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRandomVerseDto(
    @SerialName("code") val code: Int,
    @SerialName("status") val status: String,
    @SerialName("data") val `data`: Data
) {
    @Serializable
    data class Data(
        @SerialName("number") val number: Int,
        @SerialName("text") val text: String,
        @SerialName("edition") val edition: Edition,
        @SerialName("surah") val surah: Surah,
        @SerialName("numberInSurah") val numberInSurah: Int,
        @SerialName("juz") val juz: Int,
        @SerialName("manzil") val manzil: Int,
        @SerialName("page") val page: Int,
        @SerialName("ruku") val ruku: Int,
        @SerialName("hizbQuarter") val hizbQuarter: Int,
        @SerialName("sajda") val sajda: Boolean
    ) {
        @Serializable
        data class Edition(
            @SerialName("identifier") val identifier: String,
            @SerialName("language") val language: String,
            @SerialName("name") val name: String,
            @SerialName("englishName") val englishName: String,
            @SerialName("format") val format: String,
            @SerialName("type") val type: String,
            @SerialName("direction") val direction: String
        )

        @Serializable
        data class Surah(
            @SerialName("number") val number: Int,
            @SerialName("name") val name: String,
            @SerialName("englishName") val englishName: String,
            @SerialName("englishNameTranslation") val englishNameTranslation: String,
            @SerialName("numberOfAyahs") val numberOfAyahs: Int,
            @SerialName("revelationType") val revelationType: String
        )
    }
}