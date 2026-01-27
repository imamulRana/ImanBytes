package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.GetRandomVerseDto
import com.anticbyte.imanbytes.data.remote.GetTafsirDto
import com.anticbyte.imanbytes.data.remote.SurahDto
import com.anticbyte.imanbytes.data.remote.SurahEditionDto
import com.anticbyte.imanbytes.data.remote.SurahInfoDto
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.Tafsir
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.domain.toDomain
import com.anticbyte.imanbytes.domain.toSelfRecitation
import com.anticbyte.imanbytes.domain.toSurah
import com.anticbyte.imanbytes.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuranRepoImpl(
    private val ktorClient: HttpClient,
) : QuranRepo {
    override suspend fun getAllSurah(): Result<List<Surah>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = ktorClient.get(urlString = "https://api.alquran.cloud/v1/surah")
                if (response.status.isSuccess()) {
                    response.body<SurahDto>().surahData.map { it.toSurah() }
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }

    override suspend fun getTxtSurahAndTranslation(surahNumber: String): Result<List<SelfRecitation>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response =
                    ktorClient.get("https://api.alquran.cloud/v1/surah/$surahNumber/editions/quran-unicode,en.sahih")
                val data = response.body<SurahEditionDto>().responseData
                if (response.status.isSuccess()) {
                    data.map { it.toSelfRecitation() }
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }

    override suspend fun getSurahInfoByNumber(surahNumber: String): Result<SurahInfoDto> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response =
                    ktorClient.get("https://api.quran.com/api/v4/chapters/$surahNumber/info")
                if (response.status.isSuccess()) {
                    response.body<SurahInfoDto>()
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }

    override suspend fun getRandomVerse(
        verseNumber: String
    ): Result<RandomVerse> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = ktorClient.get("https://api.alquran.cloud/v1/ayah") {
                    url.appendPathSegments(verseNumber, "en.sahih")
                }
                if (response.status.isSuccess()) response.body<GetRandomVerseDto>().toDomain()
                else throw Exception(response.status.description)
            }
        }
    }

    override suspend fun getTafsir(surahNumber: String, verseNumber: String): Result<Tafsir> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = ktorClient.get("https://quranapi.pages.dev/api/tafsir") {
                    url.appendPathSegments("${surahNumber}_${verseNumber}.json")
                }
                if (response.status.isSuccess()) response.body<GetTafsirDto>().toDomain()
                else throw Exception(response.status.description)
            }
        }
    }
}