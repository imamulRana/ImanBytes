package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.data.remote.AsmaAlHusnaDto
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.repo.AsmaAlHusnaRepo
import com.anticbyte.imanbytes.domain.toAsma
import com.anticbyte.imanbytes.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class AsmaAlHusnaRepoImpl(private val httpClient: HttpClient) : AsmaAlHusnaRepo {
    override suspend fun getSingleAsma(number: String): Result<Asma> {
        return safeApiCall {
            val response = httpClient.get("https://api.aladhan.com/asmaAlHusna") {
                url.appendPathSegments(number)
            }
            response.body<AsmaAlHusnaDto>().data.map {
                it.toAsma()
            }.first()
        }
    }

    override suspend fun getAllAsma(): Result<List<Asma>> {
        return safeApiCall {
            httpClient.get("https://api.aladhan.com/asmaAlHusna").body<AsmaAlHusnaDto>().data.map {
                it.toAsma()
            }
        }
    }
}