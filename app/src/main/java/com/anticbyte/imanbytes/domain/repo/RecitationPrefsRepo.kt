package com.anticbyte.imanbytes.domain.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.anticbyte.imanbytes.di.DatastoreModule.recitationCurrentSurah
import com.anticbyte.imanbytes.di.DatastoreModule.recitationCurrentSurahTr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

interface RecitationPrefsRepo {
    suspend fun persistCurrentSurah(surahNumber: String)
    fun retrieveCurrentSurah(): Flow<String>
    suspend fun persistCurrentSurahTr(surahNumber: String)
    fun retrieveCurrentSurahTr(): Flow<String>
}

class RecitationPrefsRepoImpl(
    private val recitationDataStore: DataStore<Preferences>
) : RecitationPrefsRepo {
    override suspend fun persistCurrentSurah(surahNumber: String) {
        recitationDataStore.edit {
            it[recitationCurrentSurah] = surahNumber
        }
    }

    override fun retrieveCurrentSurah(): Flow<String> =
        recitationDataStore.data.map {
            it[recitationCurrentSurah].orEmpty()
        }.distinctUntilChanged()

    override suspend fun persistCurrentSurahTr(surahNumber: String) {
        recitationDataStore.edit {
            it[recitationCurrentSurahTr] = surahNumber
        }
    }

    override fun retrieveCurrentSurahTr(): Flow<String> {
        return recitationDataStore.data.map {
            it[recitationCurrentSurahTr].orEmpty()
        }.distinctUntilChanged()
    }
}