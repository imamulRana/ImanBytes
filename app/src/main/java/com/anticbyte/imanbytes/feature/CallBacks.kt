package com.anticbyte.imanbytes.feature

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

val MediaController.playBackStateFlow: Flow<PlayBackState>
    get() = callbackFlow {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                trySend(
                    PlayBackState(
                        isPlaying = player.isPlaying,
                        // Paused specifically means ready to play, but not playing
                        isPaused = !player.isPlaying && player.playbackState == Player.STATE_READY,
                        isControllerReady = true,
                        currentMediaId = player.currentMediaItem?.mediaId
                    )
                )
            }
        }

        addListener(listener)

        // Initial emission now that we know the controller is active
        trySend(
            PlayBackState(
                isPlaying = isPlaying,
                isPaused = !isPlaying && playbackState == Player.STATE_READY,
                isControllerReady = true,
                currentMediaId = currentMediaItem?.mediaId
            )
        )

        awaitClose { removeListener(listener) }
    }

val MediaController.mediaMetaDataFlow: Flow<PlayerUiState>
    get() = callbackFlow {
        // 1. Define the listener
        val listener = object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                // When the song changes, emit the new item
                trySend(mediaItem.toUiState)
            }

            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                // When metadata (like ID3 tags) loads, emit the updated current item
                trySend(currentMediaItem.toUiState)
            }
        }

        // 2. Register listener
        addListener(listener)

        // 3. Emit Initial State IMMEDIATELY
        // Don't wait/poll. If it's null, the UI should handle the "empty" state.
        trySend(currentMediaItem.toUiState)

        // 4. Cleanup
        awaitClose { removeListener(listener) }
    }
        // 5. Optimization: Combine rapid updates
        .conflate()


val MediaItem?.toUiState: PlayerUiState
    get() = PlayerUiState(
        title = this?.mediaMetadata?.title?.toString().orEmpty(),
        artist = this?.mediaMetadata?.artist?.toString().orEmpty(),
        mediaId = this?.mediaId,
        mediaIndex = null
    )


data class PlayBackState(
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val isControllerReady: Boolean = false, // New flag
    val currentMediaId: String? = null
)

data class PlayerUiState(
    val title: String,
    val artist: String,
    val mediaId: String?,
    val mediaIndex: String?
)