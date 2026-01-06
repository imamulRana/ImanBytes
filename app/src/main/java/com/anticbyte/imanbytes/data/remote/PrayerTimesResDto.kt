package com.anticbyte.imanbytes.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrayerTimesResDto(
    val code: Int,
    val status: String,
    val data: Data
) {

    @Serializable
    data class Data(
        val timings: Timings,
        val date: Date,
        val meta: Meta
    )

    // -------------------- Timings --------------------

    @Serializable
    data class Timings(
        @SerialName("Fajr") val fajr: String,
        @SerialName("Sunrise") val sunrise: String,
        @SerialName("Dhuhr") val dhuhr: String,
        @SerialName("Asr") val asr: String,
        @SerialName("Sunset") val sunset: String,
        @SerialName("Maghrib") val maghrib: String,
        @SerialName("Isha") val isha: String,
        @SerialName("Imsak") val imsak: String,
        @SerialName("Midnight") val midnight: String,
        @SerialName("Firstthird") val firstThird: String,
        @SerialName("Lastthird") val lastThird: String
    )

    // -------------------- Date --------------------

    @Serializable
    data class Date(
        val readable: String,
        val timestamp: String,
        val hijri: Hijri,
        val gregorian: Gregorian
    )

    @Serializable
    data class Hijri(
        val date: String,
        val format: String,
        val day: String,
        val weekday: Weekday,
        val month: HijriMonth,
        val year: String,
        val designation: Designation,
        val holidays: List<String> = emptyList(),
        val adjustedHolidays: List<String> = emptyList(),
        val method: String
    )

    @Serializable
    data class Gregorian(
        val date: String,
        val format: String,
        val day: String,
        val weekday: GregorianWeekday,
        val month: GregorianMonth,
        val year: String,
        val designation: Designation,
        val lunarSighting: Boolean
    )

    @Serializable
    data class Weekday(
        val en: String,
        val ar: String
    )

    @Serializable
    data class GregorianWeekday(
        val en: String
    )

    @Serializable
    data class HijriMonth(
        val number: Int,
        val en: String,
        val ar: String,
        val days: Int
    )

    @Serializable
    data class GregorianMonth(
        val number: Int,
        val en: String
    )

    @Serializable
    data class Designation(
        val abbreviated: String,
        val expanded: String
    )

    // -------------------- Meta --------------------

    @Serializable
    data class Meta(
        val latitude: Double,
        val longitude: Double,
        val timezone: String,
        val method: Method,
        val latitudeAdjustmentMethod: String,
        val midnightMode: String,
        val school: String,
        val offset: Offset
    )

    @Serializable
    data class Method(
        val id: Int,
        val name: String,
        val params: MethodParams,
        val location: Location
    )

    @Serializable
    data class MethodParams(
        @SerialName("Fajr") val fajr: Int,
        @SerialName("Isha") val isha: Int
    )

    @Serializable
    data class Location(
        val latitude: Double,
        val longitude: Double
    )

    // Mixed number/string values → safest as String
    @Serializable
    data class Offset(
        @SerialName("Imsak") val imsak: String,
        @SerialName("Fajr") val fajr: String,
        @SerialName("Sunrise") val sunrise: String,
        @SerialName("Dhuhr") val dhuhr: String,
        @SerialName("Asr") val asr: String,
        @SerialName("Maghrib") val maghrib: String,
        @SerialName("Sunset") val sunset: String,
        @SerialName("Isha") val isha: String,
        @SerialName("Midnight") val midnight: String
    )
}

