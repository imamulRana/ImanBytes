package com.anticbyte.imanbytes.feature

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class QuranAudioManager @Inject constructor(
    private val exoPlayer: ExoPlayer
) {
    private var isMediaSet = false
    val isPlayingFlow: Flow<Boolean> = callbackFlow {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                trySend(isPlaying)
            }
        }
        exoPlayer.addListener(listener)
        trySend(exoPlayer.isPlaying)
        awaitClose { exoPlayer.removeListener(listener) }
    }.distinctUntilChanged()
    fun playQuranAudio(audioUrl: String) {
        exoPlayer.apply {
            if (!isMediaSet) {
                setMediaItem(MediaItem.fromUri(audioUrl))
                prepare()
                isMediaSet = true
            } else if (playbackState == Player.STATE_ENDED) {
                seekTo(0L)
            }
            play()
        }
    }
    fun pauseQuranAudio() = exoPlayer.pause()
    fun releasePlayer() = exoPlayer.release()
    val currentProgress: Flow<Float> = flow {
        while (currentCoroutineContext().isActive) {
            val contentDuration = exoPlayer.contentDuration.takeIf { it > 0 } ?: 1L
            val currentPosition = exoPlayer.currentPosition
            emit(value = (currentPosition / contentDuration.toFloat()).coerceIn(0f, 1f))
            delay(100L)
        }
    }.distinctUntilChanged()
}