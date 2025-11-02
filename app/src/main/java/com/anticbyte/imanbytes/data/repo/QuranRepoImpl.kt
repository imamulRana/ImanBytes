package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.SurahEditionDto
import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.domain.toSurahText
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

    override suspend fun getTxtSurahAndTranslation(surahNumber: String): Result<Pair<List<SurahText>, List<SurahText>>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response =
                    ktorClient.get("https://api.alquran.cloud/v1/surah/$surahNumber/editions/quran-uthmani,en.sahih")
                val data = response.body<SurahEditionDto>().responseData
                if (response.status.isSuccess()) {
                    val arabic = data.first().ayahs.asSequence().map { it.toSurahText() }.toList()
                    val english = data.last().ayahs.asSequence().map { it.toSurahText() }.toList()
                    arabic to english
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }
}