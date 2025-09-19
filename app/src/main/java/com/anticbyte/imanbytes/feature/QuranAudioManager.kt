package com.anticbyte.imanbytes.feature

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.recitation.component.PlayerSeekType
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
    val playerStateFlow: Flow<PlayerState> = callbackFlow {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                trySend(
                    element = if (player.isPlaying) PlayerState.PlayerPlaying(0f)
                    else PlayerState.PlayerPaused
                )
            }
        }
        exoPlayer.addListener(listener)
        trySend(
            PlayerState.PlayerIdle
        ).isSuccess
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
                val mediaItem =
                    MediaItem.Builder().setMediaId(surahID.plus(".${recitationType.recitationId}"))
                        .setUri(
                            BuildConfig.AUDIO_BASE_URL.format(
                                recitationType.recitationId,
                                surahID
                            )
                        ).build()
                //
                setMediaItem(mediaItem)
                prepare()
                play()
            }
        }
    }

    fun seekAudio(seekType: PlayerSeekType) {
        exoPlayer.apply {
            if (playbackState == Player.STATE_READY) {
                seekTo(
                    currentMediaItemIndex, if (currentMediaItem == null) 0L
                    else {
                        if (seekType == PlayerSeekType.BACKWARD) currentPosition - seekType.seekDuration
                        else currentPosition + seekType.seekDuration
                    }
                )
            }
        }
    }

    fun pauseAudio() = exoPlayer.pause()
    fun stopAudio() = exoPlayer.stop()
    fun releasePlayer() {
        exoPlayer.clearMediaItems()
        exoPlayer.release()
    }

    val currentProgress: Flow<Float> = flow {
        while (currentCoroutineContext().isActive) {
            val contentDuration = exoPlayer.contentDuration.takeIf { it > 0 } ?: 1L
            val currentPosition = exoPlayer.currentPosition
            emit(value = (currentPosition / contentDuration.toFloat()).coerceIn(0f, 1f))
            delay(100L)
        }
    }.distinctUntilChanged()

    val audioTimeline: Flow<Pair<Long, Long>> = flow {
        while (currentCoroutineContext().isActive) {
            val contentDuration = exoPlayer.contentDuration.takeIf { it > 0 } ?: 1L
            val currentPosition = exoPlayer.currentPosition
            emit(value = Pair(currentPosition, contentDuration))
            delay(100L)
        }
    }.distinctUntilChanged()
}