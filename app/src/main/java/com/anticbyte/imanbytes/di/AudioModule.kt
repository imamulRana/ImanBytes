package com.anticbyte.imanbytes.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.feature.AudioPlaybackService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioModule {
    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
            .build()
        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true) // Automatically handle audio focus
            .setHandleAudioBecomingNoisy(true) // Automatically pause on headphone disconnect
            .build()
    }

    @Provides
    @Singleton
    fun provideSessionToken(@ApplicationContext context: Context):
            SessionToken =
        SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))
}
