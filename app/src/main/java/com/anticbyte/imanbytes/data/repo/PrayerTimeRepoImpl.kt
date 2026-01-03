package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.PrayerTimesResDto
import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import com.anticbyte.imanbytes.domain.toPrayerTime
import com.anticbyte.imanbytes.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.http.path
import javax.inject.Inject

class PrayerTimeRepoImpl @Inject constructor(private val httpClient: HttpClient) : PrayerTimeRepo {
    override suspend fun getPrayerTimes(date: String): Result<PrayerTime> {
        return safeApiCall {
            val response = httpClient.get("https://api.aladhan.com/v1/timings/") {
                url {
                    path(date)
                    parameters.append("latitude", "23.68")
                    parameters.append("longitude", "90.36")
                    parameters.append("timezonestring", "Asia/Dhaka")
                }
            }
            if (response.status.isSuccess()) {
                response.body<PrayerTimesResDto>().data.timings.toPrayerTime()
            } else {
                throw Exception(response.status.description)
            }
        }
    }
}