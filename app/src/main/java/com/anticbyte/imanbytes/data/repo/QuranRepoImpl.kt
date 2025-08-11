package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.google.android.gms.time.TrustedTimeClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class QuranRepoImpl(
    private val trustedTime: TrustedTimeClient,
    private val ktorClient: HttpClient,
) : QuranRepo {
    override suspend fun getQuranData(): Result<Quran> {
        return runCatching {
            val currentTime = trustedTime.computeCurrentUnixEpochMillis() ?: 0L
            val result = ktorClient.get("").body<Quran>()

            if (currentTime < (result.verseDetail?.completeTime ?: 0L)) {
                throw Exception("Time is not valid")
            } else {
                result
            }
        }
    }
}