package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.SurahDto
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import com.anticbyte.imanbytes.domain.toSurah
import com.anticbyte.imanbytes.utils.apiSafeCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecitationRepoImpl(private val httpClient: HttpClient) : RecitationRepo {
    override suspend fun getAllSurah(): Result<List<Surah>> {
        return withContext(Dispatchers.IO) {
            apiSafeCall {
                val response = httpClient.get(urlString = "https://api.alquran.cloud/v1/surah")
                if (response.status.isSuccess()) {
                    response.body<SurahDto>().surahData.map { it.toSurah() }
                } else {
                    throw Exception(response.status.description)
                }
            }
        }
    }

    override suspend fun getTranslatedRecitation(): Result<List<Surah>> {
        TODO("Not yet implemented")
    }
}