package com.anticbyte.imanbytes.domain.repo

import com.anticbyte.imanbytes.data.remote.SurahInfoDto
import com.anticbyte.imanbytes.domain.model.QuranSearch
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.Tafsir

interface QuranRepo {
    suspend fun getAllSurah(): Result<List<Surah>>
    suspend fun getTxtSurahAndTranslation(surahNumber: String): Result<List<SelfRecitation>>
    suspend fun getSurahInfoByNumber(surahNumber: String): Result<SurahInfoDto>
    suspend fun getRandomVerse(verseNumber: String): Result<RandomVerse>
    suspend fun getTafsir(surahNumber: String, verseNumber: String): Result<Tafsir>
    suspend fun searchQuran(query: String) : Result<QuranSearch>
}