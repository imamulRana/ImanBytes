package com.anticbyte.imanbytes.di

import android.content.Context
import com.anticbyte.imanbytes.data.repo.AsmaAlHusnaRepoImpl
import com.anticbyte.imanbytes.data.repo.PrayerTimeRepoImpl
import com.anticbyte.imanbytes.data.repo.QuranRepoImpl
import com.anticbyte.imanbytes.domain.repo.AsmaAlHusnaRepo
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.utils.jsonConfig
import com.anticbyte.imanbytes.utils.loggingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkService(@ApplicationContext context: Context): HttpClient =
        HttpClient(Android) {
            install(plugin = ContentNegotiation, configure = jsonConfig)
            install(plugin = Logging, configure = loggingConfig)
            install(HttpCache) {
                val cacheFolder = File(context.cacheDir, "http_cache")

                // 2. Ensure the directory actually exists before Ktor tries to use it
                if (!cacheFolder.exists()) {
                    cacheFolder.mkdirs()
                }
                publicStorage(FileStorage(context.filesDir))
            }
            install(HttpRequestRetry) {
                retryOnException(5, true)
                exponentialDelay()
            }
        }

    @Provides
    @Singleton
    fun provideQuranRepo(httpClient: HttpClient): QuranRepo =
        QuranRepoImpl(ktorClient = httpClient)

    @Provides
    @Singleton
    fun providePrayerTimeRepo(httpClient: HttpClient): PrayerTimeRepo =
        PrayerTimeRepoImpl(httpClient = httpClient)

    @Provides
    @Singleton
    fun provideAsmaAlHusnaRepo(httpClient: HttpClient): AsmaAlHusnaRepo =
        AsmaAlHusnaRepoImpl(httpClient = httpClient)
}
