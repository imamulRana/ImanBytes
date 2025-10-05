package com.anticbyte.imanbytes.domain.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.anticbyte.imanbytes.di.DatastoreModule.recitationCurrentSurah
import com.anticbyte.imanbytes.di.DatastoreModule.recitationPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

interface RecitationPrefsRepo {
    suspend fun persistRecitationPrefs(prefs: RecitationPrefs)
    fun retrieveRecitationPrefs(): Flow<RecitationPrefs>

    suspend fun persistCurrentSurah(surahNumber: String)
    fun retrieveCurrentSurah(): Flow<String>
}

class RecitationPrefsRepoImpl(
    private val recitationDataStore: DataStore<Preferences>
) : RecitationPrefsRepo {
    override suspend fun persistRecitationPrefs(prefs: RecitationPrefs) {
        recitationDataStore.edit {
            it[recitationPrefs] = Json.encodeToString(prefs)
        }
    }

    override fun retrieveRecitationPrefs(): Flow<RecitationPrefs> {
        return recitationDataStore.data.map {
            val data = it[recitationPrefs]
            Json.decodeFromString<RecitationPrefs>(data.orEmpty())
        }
    }

    override suspend fun persistCurrentSurah(surahNumber: String) {
        recitationDataStore.edit {
            it[recitationCurrentSurah] = surahNumber
        }
    }

    override fun retrieveCurrentSurah(): Flow<String> =
        recitationDataStore.data.map {
            it[recitationCurrentSurah].orEmpty()
        }.distinctUntilChanged()
}

data class RecitationPrefs(
    val surahNumber: String,
    val currentPosition: Long
)