package com.anticbyte.imanbytes.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.exoplayer.ExoPlayer
import com.anticbyte.imanbytes.data.repo.QuranRepoFakeImpl
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.utils.jsonConfig
import com.anticbyte.imanbytes.utils.loggingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkService(): HttpClient =
        HttpClient(CIO) {
            install(plugin = ContentNegotiation, configure = jsonConfig)
            install(plugin = Logging, configure = loggingConfig)
        }

    @Provides
    @Singleton
    fun provideQuranRepo(): QuranRepo = QuranRepoFakeImpl()

    @Provides
    @Singleton
    fun providePlayer(@ApplicationContext context: Context): ExoPlayer =
        ExoPlayer.Builder(context).setAudioAttributes(AudioAttributes.DEFAULT, true).build()

    @Provides
    @Singleton
    fun provideQuranAudioManager(exoPlayer: ExoPlayer): QuranAudioManager =
        QuranAudioManager(exoPlayer)
}
