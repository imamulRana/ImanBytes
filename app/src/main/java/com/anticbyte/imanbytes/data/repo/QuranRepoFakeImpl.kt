package com.anticbyte.imanbytes.data.repo

import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.utils.QuranDataFake.quranData
import com.google.android.gms.time.TrustedTimeClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import javax.inject.Inject


class QuranRepoFakeImpl  : QuranRepo {
    override suspend fun getQuranData(): Result<Quran> {
        return runCatching {
            delay(2000)
            quranData
//            throw Exception("Time is not valid")
        }
    }
}