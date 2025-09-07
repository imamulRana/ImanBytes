package com.anticbyte.imanbytes.feature

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class QuranAudioManager @Inject constructor(
    private val exoPlayer: ExoPlayer
) {
    val isPlayingFlow: Flow<Boolean> = callbackFlow {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                trySend(isPlaying).isSuccess
            }
        }
        exoPlayer.addListener(listener)
        trySend(exoPlayer.isPlaying).isSuccess
        awaitClose { exoPlayer.removeListener(listener) }
    }.distinctUntilChanged()

    val audioTrackFlow: Flow<String> = callbackFlow {
        val listener = object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                trySend(element = mediaItem?.mediaId.orEmpty())
            }
        }
        exoPlayer.addListener(listener)
        val currentMediaItem = exoPlayer.currentMediaItem
        trySend(currentMediaItem?.mediaId.orEmpty()).isSuccess
        awaitClose { exoPlayer.removeListener(listener) }
    }.distinctUntilChanged()

    fun playOrToggle(surahID: String, recitationType: RecitationType) {
        exoPlayer.apply {
            //checks if the media id is == to the surah number
            if (currentMediaItem?.mediaId == surahID.plus(".${recitationType.recitationId}")) {
                // if is player is active then pause else play
                if (isPlaying) pause()
                else if (playbackState == Player.STATE_ENDED) {
                    seekTo(0L); play()
                } else play()
            } else {
                //if different surah play new surah
                val mediaItem = MediaItem.Builder()
                    .setMediaId(surahID.plus(".${recitationType.recitationId}"))
                    .setUri(BuildConfig.AUDIO_BASE_URL.format(recitationType.recitationId, surahID))
                    .build()
                //
                setMediaItem(mediaItem)
                prepare()
                play()
            }
        }
    }

    fun pauseAudio() = exoPlayer.pause()
    fun stopAudio() = exoPlayer.stop()
    fun releasePlayer() {
        exoPlayer.release()
        exoPlayer.clearMediaItems()
    }

    val currentProgress: Flow<Float> = flow {
        while (currentCoroutineContext().isActive) {
            val contentDuration = exoPlayer.contentDuration.takeIf { it > 0 } ?: 1L
            val currentPosition = exoPlayer.currentPosition
            emit(value = (currentPosition / contentDuration.toFloat()).coerceIn(0f, 1f))
            delay(100L)
        }
    }.distinctUntilChanged()
}