package com.anticbyte.imanbytes.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuranDto(
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
        @SerialName("numberOfAyahs") val numberOfAyahs: Int,
        @SerialName("revelationType") val revelationType: String
    )
}