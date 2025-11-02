package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.SurahText

interface QuranRepo {
    suspend fun getQuranData(): Result<Quran>
    suspend fun getAllSurah(): Result<List<Surah>>
    suspend fun getTxtSurahAndTranslation(surahNumber: String): Result<Pair<List<SurahText>, List<SurahText>>>
}