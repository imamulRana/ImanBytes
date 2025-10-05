package com.anticbyte.imanbytes.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SurahEditionDto(
    @SerialName("code") val code: Int,
    @SerialName("status") val status: String,
    @SerialName("data") val responseData: List<ResponseData>
) {
    @Serializable
    data class ResponseData(
        @SerialName("number") val number: Int,
        @SerialName("name") val name: String,
        @SerialName("englishName") val englishName: String,
        @SerialName("englishNameTranslation") val englishNameTranslation: String,
        @SerialName("revelationType") val revelationType: String,
        @SerialName("numberOfAyahs") val numberOfAyahs: Int,
        @SerialName("ayahs") val ayahs: List<Ayah>,
        @SerialName("edition") val edition: Edition
    ) {
        @Serializable
        data class Ayah(
            @SerialName("number") val number: Int,
            @SerialName("text") val text: String,
            @SerialName("numberInSurah") val numberInSurah: Int,
            @SerialName("juz") val juz: Int,
            @SerialName("manzil") val manzil: Int,
            @SerialName("page") val page: Int,
            @SerialName("ruku") val ruku: Int,
            @SerialName("hizbQuarter") val hizbQuarter: Int,
            @SerialName("sajda") val sajda: JsonElement
        )
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
    }
}