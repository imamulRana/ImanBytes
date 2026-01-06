package com.anticbyte.imanbytes.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HijriToGregorianResponseDto(
    @SerialName("code") val code: Int,
    @SerialName("status") val status: String,
    @SerialName("data") val `data`: List<Data>
) {
    @Serializable
    data class Data(
        @SerialName("hijri") val hijri: Hijri,
        @SerialName("gregorian") val gregorian: Gregorian
    ) {
        @Serializable
        data class Hijri(
            @SerialName("date") val date: String,
            @SerialName("format") val format: String,
            @SerialName("day") val day: String,
            @SerialName("weekday") val weekday: Weekday,
            @SerialName("month") val month: Month,
            @SerialName("year") val year: String,
            @SerialName("designation") val designation: Designation,
            @SerialName("holidays") val holidays: List<String>,
            @SerialName("adjustedHolidays") val adjustedHolidays: List<String>,
            @SerialName("method") val method: String
        ) {
            @Serializable
            data class Weekday(
                @SerialName("en") val en: String,
                @SerialName("ar") val ar: String
            )

            @Serializable
            data class Month(
                @SerialName("number") val number: Int,
                @SerialName("en") val en: String,
                @SerialName("ar") val ar: String,
                @SerialName("days") val days: Int
            )

            @Serializable
            data class Designation(
                @SerialName("abbreviated") val abbreviated: String,
                @SerialName("expanded") val expanded: String
            )
        }

        @Serializable
        data class Gregorian(
            @SerialName("date") val date: String,
            @SerialName("format") val format: String,
            @SerialName("day") val day: String,
            @SerialName("weekday") val weekday: Weekday,
            @SerialName("month") val month: Month,
            @SerialName("year") val year: String,
            @SerialName("designation") val designation: Designation,
            @SerialName("lunarSighting") val lunarSighting: Boolean
        ) {
            @Serializable
            data class Weekday(
                @SerialName("en") val en: String
            )

            @Serializable
            data class Month(
                @SerialName("number") val number: Int,
                @SerialName("en") val en: String
            )

            @Serializable
            data class Designation(
                @SerialName("abbreviated") val abbreviated: String,
                @SerialName("expanded") val expanded: String
            )
        }
    }
}