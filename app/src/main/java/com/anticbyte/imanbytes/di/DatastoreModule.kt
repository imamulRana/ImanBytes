package com.anticbyte.imanbytes.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.data.repo.UserPrefsRepoImpl
import com.anticbyte.imanbytes.domain.repo.RecitationPrefsRepo
import com.anticbyte.imanbytes.domain.repo.RecitationPrefsRepoImpl
import com.anticbyte.imanbytes.domain.repo.UserPrefsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
    val Context.userDataStore: DataStore<Preferences>
            by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_user_preferences")
    val Context.recitationDataStore: DataStore<Preferences>
            by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_recitation_preferences")
    val userNavigationPrefs = booleanPreferencesKey("user_navigation_prefs")
    val recitationCurrentSurah = stringPreferencesKey("recitation_current_surah")
    val recitationCurrentSurahTr = stringPreferencesKey("recitation_current_surah_tr")

    @Provides
    @Singleton
    fun provideUserDatastore(
        @ApplicationContext context: Context,
    ): UserPrefsRepo = UserPrefsRepoImpl(userDataStorePrefs = context.userDataStore)

    @Provides
    @Singleton
    fun provideRecitationDatastore(
        @ApplicationContext context: Context,
    ): RecitationPrefsRepo =
        RecitationPrefsRepoImpl(recitationDataStore = context.recitationDataStore)
}