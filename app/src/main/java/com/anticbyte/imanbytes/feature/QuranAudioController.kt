package com.anticbyte.imanbytes.feature

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.domain.model.Surah
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        controllerFuture.addListener(
            { _controller.value = controllerFuture.get() },
            MoreExecutors.directExecutor()
        )
    }

    fun createMediaItem(surahs: List<Surah>) {
        val controller = _controller.value ?: return
        if (controller.mediaItemCount > 0) return // prevent reset

        controller.setMediaItems(surahs.toMediaItems())
        controller.prepare()
    }

    fun playAudio() {
        val controller = _controller.value ?: return
        if (!controller.isPlaying) {
            controller.prepare()
            controller.play()
        }
    }

    fun pauseAudio() {
        val controller = _controller.value ?: return
        if (controller.isPlaying) {
            controller.pause()
        }
    }

    fun List<Surah>.toMediaItems(): List<MediaItem> = map { surah ->
        MediaItem.Builder()
            .setUri(BuildConfig.AUDIO_BASE_URL.format("ar.alafasy", surah.number))
            .setMediaId(surah.number)
            .setTag(surah)
            .build()
    }

    fun releaseFuture() {
        MediaController.releaseFuture(controllerFuture)
        _controller.value = null
    }
}