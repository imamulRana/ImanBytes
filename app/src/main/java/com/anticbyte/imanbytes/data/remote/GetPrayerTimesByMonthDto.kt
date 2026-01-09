package com.anticbyte.imanbytes.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetPrayerTimesByMonthDto(
    @SerialName("code") val code: Int,

    @SerialName("status") val status: String,

    @SerialName("data") val data: List<Data>
) {

    @Serializable
    data class Data(
        @SerialName("timings") val timings: Timings,

        @SerialName("date") val date: Date,

        @SerialName("meta") val meta: Meta
    ) {

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

        @Serializable
        data class Date(
            @SerialName("readable") val readable: String,

            @SerialName("timestamp") val timestamp: String,

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
                    @SerialName("en") val en: String, @SerialName("ar") val ar: String
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
                    @SerialName("number") val number: Int, @SerialName("en") val en: String
                )

                @Serializable
                data class Designation(
                    @SerialName("abbreviated") val abbreviated: String,
                    @SerialName("expanded") val expanded: String
                )
            }
        }

        @Serializable
        data class Meta(
            @SerialName("latitude") val latitude: Double,
            @SerialName("longitude") val longitude: Double,
            @SerialName("timezone") val timezone: String,
            @SerialName("method") val method: Method,
            @SerialName("latitudeAdjustmentMethod") val latitudeAdjustmentMethod: String,
            @SerialName("midnightMode") val midnightMode: String,
            @SerialName("school") val school: String,
            @SerialName("offset") val offset: Offset
        ) {

            @Serializable
            data class Method(
                @SerialName("id") val id: Int,
                @SerialName("name") val name: String,
                @SerialName("params") val params: Params,
                @SerialName("location") val location: Location
            ) {

                @Serializable
                data class Params(
                    @SerialName("Fajr") val fajr: Int, @SerialName("Isha") val isha: Int
                )

                @Serializable
                data class Location(
                    @SerialName("latitude") val latitude: Double,
                    @SerialName("longitude") val longitude: Double
                )
            }

            @Serializable
            data class Offset(
                @SerialName("Imsak") val imsak: Int,
                @SerialName("Fajr") val fajr: Int,
                @SerialName("Sunrise") val sunrise: Int,
                @SerialName("Dhuhr") val dhuhr: Int,
                @SerialName("Asr") val asr: Int,
                @SerialName("Sunset") val sunset: Int,
                @SerialName("Maghrib") val maghrib: Int,
                @SerialName("Isha") val isha: Int,
                @SerialName("Midnight") val midnight: Int
            )
        }
    }
}
