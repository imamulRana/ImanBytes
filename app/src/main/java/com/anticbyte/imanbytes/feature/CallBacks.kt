package com.anticbyte.imanbytes.feature

import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

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

val MediaController.metadataFlow: Flow<MetadataUiState>
    get() = callbackFlow {

        // Helper to map MediaMetadata to your UI State safely
        fun MediaMetadata.toUiState() = MetadataUiState(
            // Use ?.toString() followed by ?: to avoid the literal "null" string
            title = title?.toString() ?: "Unknown Title",
            artist = artist?.toString() ?: "Unknown Artist",
            mediaId = null // Replace with actual ID logic if needed
        )

        // Send current state immediately
        trySend(mediaMetadata.toUiState())

        val listener = object : Player.Listener {
            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                trySend(mediaMetadata.toUiState())
            }
        }
        addListener(listener)
        awaitClose { removeListener(listener) }
    }.distinctUntilChanged()


fun MediaMetadata.toUiState() = MetadataUiState(
    // .isNotBlank() check ensures we don't overwrite with empty strings
    // if we already had data from the previous item
    title = if (!title.isNullOrBlank()) title.toString() else "Loading...",
    artist = if (!artist.isNullOrBlank()) artist.toString() else "Please wait...",
    mediaId = null
)

data class PlayBackState(
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val isControllerReady: Boolean = false, // New flag
    val currentMediaId: String? = null
)

data class MetadataUiState(
    val title: String,
    val artist: String,
    val mediaId: String?
) {
    companion object {
        val EMPTY = MetadataUiState("", "", null)
    }
}