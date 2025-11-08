package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.data.remote.SurahInfoDto
import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.utils.SurahInfo

interface QuranRepo {
    suspend fun getQuranData(): Result<Quran>
    suspend fun getAllSurah(): Result<List<Surah>>
    suspend fun getTxtSurahAndTranslation(surahNumber: String): Result<List<SelfRecitation>>
    suspend fun getSurahInfoByNumber(surahNumber: String): Result<SurahInfoDto>
}