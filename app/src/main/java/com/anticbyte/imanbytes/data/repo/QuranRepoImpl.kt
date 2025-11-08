package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.SurahEditionDto
import com.anticbyte.imanbytes.data.remote.SurahInfoDto
import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.domain.toSelfRecitation
import com.anticbyte.imanbytes.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuranRepoImpl(
    private val ktorClient: HttpClient,
) : QuranRepo {
    override suspend fun getQuranData(): Result<Quran> {
        TODO()
    }

    override suspend fun getAllSurah(): Result<List<Surah>> {
        TODO("Not yet implemented")
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
}