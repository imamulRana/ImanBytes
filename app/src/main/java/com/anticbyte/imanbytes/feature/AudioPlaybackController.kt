package com.anticbyte.imanbytes.feature

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlaybackController @Inject constructor(@ApplicationContext val context: Context) {
    private var mediaController: MediaController? = null
    private var controllerFuture: ListenableFuture<MediaController>

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))

        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

        controllerFuture.addListener(
            { mediaController = controllerFuture.get() },
            ContextCompat.getMainExecutor(context)
        )
    }

    fun playPauseMedia(surahNumber: String, recitationType: RecitationType) {
        val mediaId = surahNumber.plus(".${recitationType.recitationId}")
        val uri = BuildConfig.AUDIO_BASE_URL.format(recitationType.recitationId, surahNumber)
        mediaController?.apply {
            if (currentMediaItem?.mediaId == mediaId) {
                if (isPlaying) pause()
                else if (playbackState == Player.STATE_ENDED) {
                    seekTo(0L); play()
                } else play()
            } else {
                setMediaItem(MediaItem.Builder().setMediaId(mediaId).setUri(uri).build())
                prepare()
                play()
            }
        }
    }
}