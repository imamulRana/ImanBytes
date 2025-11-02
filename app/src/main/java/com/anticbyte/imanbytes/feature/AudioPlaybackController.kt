package com.anticbyte.imanbytes.feature

import android.content.ComponentName
import android.content.Context
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlaybackController @Inject constructor(@ApplicationContext val context: Context) {
    val sessionToken =
        SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))
    val controllerFuture = MediaController
        .Builder(context, sessionToken)
        .buildAsync()

    @OptIn(UnstableApi::class)
    fun setupMediaController() {
        controllerFuture.addListener(
            {
                val mediaController = controllerFuture.get()
                mediaController.setMediaItem(
                    MediaItem.Builder()
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .build()
                        ).build()
                )
                mediaController.prepare()
                mediaController.play()
            },
            ContextCompat.getMainExecutor(context)
        )
    }
}