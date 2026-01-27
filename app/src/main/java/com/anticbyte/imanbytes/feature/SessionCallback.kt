package com.anticbyte.imanbytes.feature

import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.anticbyte.imanbytes.BuildConfig
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

val sessionCallback = @UnstableApi
object : MediaSession.Callback {
    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>
    ): ListenableFuture<List<MediaItem>> {
        val updatedMediaItems = mediaItems.map { mediaItem ->
            val audioId = mediaItem.mediaId.split('_')
            val audioUri = BuildConfig.AUDIO_BASE_URL.format(
                audioId[0], audioId[1]
            )

            mediaItem.buildUpon()
                .setUri(audioUri)
                .setMediaMetadata(mediaItem.mediaMetadata)
                .build()
        }
        return Futures.immediateFuture(updatedMediaItems)
    }

    /*override fun onPlaybackResumption(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        isForPlayback: Boolean
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {

        // Example: resume last played surah
        val lastReciterId = "ar.alafasy"
        val lastSurahNumber = 12

        val mediaId = "$lastReciterId|$lastSurahNumber"

        val mediaItem = MediaItem.Builder()
            .setMediaId(mediaId)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Surah $lastSurahNumber")
                    .setArtist(lastReciterId)
                    .setIsPlayable(true)
                    .build()
            )
            .build()

        return Futures.immediateFuture(
            MediaSession.MediaItemsWithStartPosition(
                listOf(mediaItem), 0, C.TIME_UNSET
            )
        )
    }*/

}