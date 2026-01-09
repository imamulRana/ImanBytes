package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.domain.model.PrayerTime

interface PrayerTimeRepo {
    suspend fun getPrayerTimes(date: String): Result<PrayerTime>
    suspend fun ramadanPrayerTimesGet(): Result<PrayerTime>
}