package com.anticbyte.imanbytes.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurahDto(
    @SerialName("code") val code: Int,
    @SerialName("status") val status: String,
    @SerialName("data") val surahData: List<SurahData>
) {
    @Serializable
    data class SurahData(
        @SerialName("number") val number: Int,
        @SerialName("name") val name: String,
        @SerialName("englishName") val englishName: String,
        @SerialName("englishNameTranslation") val englishNameTranslation: String,
        @SerialName("numberOfAyahs") val numberOfAyahs: Int,
        @SerialName("revelationType") val revelationType: String
    )
}