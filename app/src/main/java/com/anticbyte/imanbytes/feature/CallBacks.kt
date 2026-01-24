package com.anticbyte.imanbytes.feature

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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


data class PlayBackState(
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val isControllerReady: Boolean = false, // New flag
    val currentMediaId: String? = null
)