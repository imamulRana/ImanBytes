package com.anticbyte.imanbytes.domain.repo

import androidx.media3.common.Player
import com.anticbyte.imanbytes.domain.model.Quran

interface QuranRepo {
    suspend fun getQuranData(): Result<Quran>

}