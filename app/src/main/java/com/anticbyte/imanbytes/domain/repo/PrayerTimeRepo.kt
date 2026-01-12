package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.domain.model.RamadanCalender

interface PrayerTimeRepo {
    suspend fun getPrayerTimes(date: String): Result<List<Pair<String, String>>>
    suspend fun ramadanPrayerTimesGet(): Result<List<RamadanCalender>>
}