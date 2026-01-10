package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.domain.model.RamadanCalender

interface PrayerTimeRepo {
    suspend fun getPrayerTimes(date: String): Result<PrayerTime>
    suspend fun ramadanPrayerTimesGet(): Result<List<RamadanCalender>>
}