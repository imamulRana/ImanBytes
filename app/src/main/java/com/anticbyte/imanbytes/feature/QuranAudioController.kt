package com.anticbyte.imanbytes.feature

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.domain.model.Surah
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class QuranAudioController @Inject constructor(@ApplicationContext private val context: Context) {
    private val sessionToken =
        SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))

    private val controllerFuture: ListenableFuture<MediaController> =
        MediaController.Builder(context, sessionToken).buildAsync()

    private val _mediaItems = MutableStateFlow(MediaItem.fromUri(""))
    val mediaItem = _mediaItems.asStateFlow()

    private val _controller = MutableStateFlow<MediaController?>(null)
    val controller: StateFlow<MediaController?> = _controller.asStateFlow()
    // Track currently playing surah number
    private val _currentPlayingSurah = MutableStateFlow<String?>(null)
    val currentPlayingSurah: StateFlow<String?> = _currentPlayingSurah.asStateFlow()

    // Track playback state
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()


    init {
        controllerFuture.addListener(
            { _controller.value = controllerFuture.get() },
            MoreExecutors.directExecutor()
        )
    }
    val playerState: Flow<Boolean> = callbackFlow {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                trySend(isPlaying)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    trySend(false)
                }
            }
        }
        _controller.value?.addListener(listener)
        awaitClose {
            _controller.value?.removeListener(listener)
        }
    }

    private fun setupListener() {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                _currentPlayingSurah.value = mediaItem?.mediaId
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    _isPlaying.value = false
                }
            }
        }
        _controller.value?.addListener(listener)
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