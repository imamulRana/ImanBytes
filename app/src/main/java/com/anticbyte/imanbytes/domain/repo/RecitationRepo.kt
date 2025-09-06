package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.domain.model.Surah

interface RecitationRepo {
    suspend fun getAllSurah(): Result<List<Surah>>
}