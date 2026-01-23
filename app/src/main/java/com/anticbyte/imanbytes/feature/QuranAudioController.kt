package com.anticbyte.imanbytes.feature

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.domain.model.Surah
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QuranAudioController @Inject constructor(@ApplicationContext private val context: Context) {
    private val sessionToken =
        SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))

    private val _isPlayerReady = MutableStateFlow(false)
    val isPlayerReady: StateFlow<Boolean> = _isPlayerReady

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused = _isPaused.asStateFlow()
    val lst = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                _isPlaying.value = true
                _isPaused.value = false
            } else {
                _isPlaying.value = false
                _isPaused.value = true
            }
        }

    }

    private val controllerFuture: ListenableFuture<MediaController> =
        MediaController.Builder(context, sessionToken)
            .setListener(
                object : MediaController.Listener {
                    override fun onCustomCommand(
                        controller: MediaController,
                        command: SessionCommand,
                        args: Bundle
                    ): ListenableFuture<SessionResult> {
                        controller.addListener(lst)
                        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
                    }
                }
            ).buildAsync()


    private val _controller = MutableStateFlow<MediaController?>(null)
    val controller: StateFlow<MediaController?> = _controller.asStateFlow()

    // Track currently playing surah number
    private val _currentPlayingSurah = MutableStateFlow<String?>(null)
    val currentPlayingSurah: StateFlow<String?> = _currentPlayingSurah.asStateFlow()

// Track playback state


    init {
        controllerFuture.addListener(
            {
                _controller.value = controllerFuture.get()
                _controller.value?.addListener(lst)
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    fun createMediaItem(surah: List<Surah>, recitationId: String) {
        val controller = _controller.value ?: return
        if (controller.mediaItemCount > 0) return
        controller.setMediaItems(surah.toMediaItems(recitationId))
        controller.prepare()
    }

    // Toggle play/pause for a specific surah
    fun togglePlayPause(surahNumber: String) {
        val controller = _controller.value ?: return

        // Find the index of the surah in the playlist
        val targetIndex = (0 until controller.mediaItemCount)
            .firstOrNull { controller.getMediaItemAt(it).mediaId == surahNumber }
            ?: return

        when (_currentPlayingSurah.value) {
            surahNumber if controller.isPlaying -> {
                controller.pause()
            }
            // If this surah is paused, resume it
            surahNumber if !controller.isPlaying -> {
                controller.play()
            }
            // If a different surah is selected, seek to it and play
            else -> {
                controller.seekToDefaultPosition(targetIndex)
                controller.playWhenReady = true
                controller.play()
            }
        }
    }

    fun onPlay(surahNumber: String) {
        _controller.value?.play()
    }

    fun List<Surah>.toMediaItems(recitationId: String): List<MediaItem> = map { surah ->
        MediaItem.Builder()
            .setUri(BuildConfig.AUDIO_BASE_URL.format(recitationId, surah.number))
            .setMediaId(surah.number)
            .setTag(surah)
            .build()
    }

    fun releaseFuture() {
        MediaController.releaseFuture(controllerFuture)
        _controller.value = null
    }
}
