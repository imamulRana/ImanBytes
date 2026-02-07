package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.GetPrayerTimesByMonthDto
import com.anticbyte.imanbytes.data.remote.PrayerTimesResDto
import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import com.anticbyte.imanbytes.domain.toPrayerTime
import com.anticbyte.imanbytes.domain.toRamadanCalendar
import com.anticbyte.imanbytes.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PrayerTimeRepoImpl @Inject constructor(private val httpClient: HttpClient) : PrayerTimeRepo {
    override suspend fun getPrayerTimes(date: String): Result<PrayerTime> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = httpClient.get("https://api.aladhan.com") {
                    url {
                        appendPathSegments("v1", "timings", date)
                        parameters.append("latitude", "23.68")
                        parameters.append("longitude", "90.36")
                        parameters.append("timezonestring", "Asia/Dhaka")
                    }
                }
                if (response.status.isSuccess()) {
                    response.body<PrayerTimesResDto>().data.toPrayerTime()
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }

    override suspend fun ramadanPrayerTimesGet(): Result<List<RamadanCalender>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = httpClient.get("https://api.aladhan.com/v1/hijriCalendar/") {
                    url {
                        appendPathSegments("1447", "9")
                        parameters.append("latitude", "23.68")
                        parameters.append("longitude", "90.36")
                        parameters.append("timezonestring", "Asia/Dhaka")
                    }
                }
                if (response.status.isSuccess()) {
                    response.body<GetPrayerTimesByMonthDto>().toRamadanCalendar()
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }
}