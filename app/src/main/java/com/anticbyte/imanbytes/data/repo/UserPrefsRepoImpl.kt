package com.anticbyte.imanbytes.data.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.anticbyte.imanbytes.di.DatastoreModule.userNavigationPrefs
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPrefsRepoImpl @Inject constructor(
    private val userDataStorePrefs: DataStore<Preferences>
) : UserPrefsRepo {
    override suspend fun persistLoginResponse() {
        userDataStorePrefs.edit {

            it.clear()
            it[stringPreferencesKey("")]
        }
        //TODO
    }

    override suspend fun retrieveLoginResponse() {
        //TODO
    }

    override suspend fun persistNavigationState(isNavigationOnBoarded: Boolean) {
        userDataStorePrefs.edit {
            it[userNavigationPrefs] = isNavigationOnBoarded
            Log.d("Persist", "persistNavigationState: $isNavigationOnBoarded")
        }
    }

    override suspend fun retrieveNavigationState(): Boolean {


        val x = userDataStorePrefs.data.map { it[userNavigationPrefs] }.first() ?: false
        Log.d("Retrieve", "retrieveNavigationState: $x")
        return x
    }
}