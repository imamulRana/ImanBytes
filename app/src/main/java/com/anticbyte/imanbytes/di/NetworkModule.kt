package com.anticbyte.imanbytes.di

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
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkService(): HttpClient =
        HttpClient(Android) {
            install(plugin = ContentNegotiation, configure = jsonConfig)
            install(plugin = ContentEncoding) {
                gzip(0.9F)
            }
            install(plugin = Logging, configure = loggingConfig)
            install(HttpRequestRetry) {
                retryOnException(5, true)
                exponentialDelay()
            }
        }

    @Provides
    @Singleton
    fun provideQuranRepo(httpClient: HttpClient): QuranRepo = QuranRepoImpl(ktorClient = httpClient)

    @Provides
    @Singleton
    fun providePrayerTimeRepo(httpClient: HttpClient): PrayerTimeRepo =
        PrayerTimeRepoImpl(httpClient = httpClient)

    @Provides
    @Singleton
    fun provideAsmaAlHusnaRepo(httpClient: HttpClient): AsmaAlHusnaRepo =
        AsmaAlHusnaRepoImpl(httpClient = httpClient)
}
