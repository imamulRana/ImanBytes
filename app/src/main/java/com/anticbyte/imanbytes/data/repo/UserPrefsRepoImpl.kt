package com.anticbyte.imanbytes.data.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.anticbyte.imanbytes.di.DatastoreModule.userNavigationPrefs
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPrefsRepoImpl @Inject constructor(
    private val userDataStorePrefs: DataStore<Preferences>
) : UserPrefsRepo {
    override suspend fun persistNavigationState(onBoarded: Boolean) {
        userDataStorePrefs.edit {
            it[userNavigationPrefs] = onBoarded
            Log.d("Persist", "persistNavigationState: $onBoarded")
        }
    }

    override fun retrieveNavigationState(): Flow<Boolean> {
        return userDataStorePrefs.data.map { it[userNavigationPrefs] ?: false }
    }
}