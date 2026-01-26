package com.anticbyte.imanbytes.feature

import android.content.ComponentName
import android.content.Context
import androidx.compose.foundation.content.MediaType
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class QuranAudioController @Inject constructor(@ApplicationContext private val context: Context) {
    private val _controller = MutableStateFlow<MediaController?>(null)
    val controller: StateFlow<MediaController?> = _controller.asStateFlow()
    private val sessionToken =
        SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))

    @OptIn(ExperimentalCoroutinesApi::class)
    val playBackState: Flow<PlayBackState> = _controller.flatMapLatest { cont ->
        cont?.playBackStateFlow ?: flowOf(PlayBackState(isPlaying = false, isPaused = false))
    }
    private val controllerFuture: ListenableFuture<MediaController> =
        MediaController.Builder(context, sessionToken).buildAsync()

    init {
        controllerFuture.addListener(
            {
                runCatching {
                    _controller.value = controllerFuture.get()
                }.onFailure {
                    it.printStackTrace()
                }
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
    fun playSurah(
        surahs: List<Surah>,
        recitationId: String,
        targetSurahNumber: String
    ) {
        val controller = _controller.value ?: return

        // Check if we are switching to a different set of audio
        // We check the first item's ID or a custom tag to see if it matches
        val firstIdInQueue = if (controller.mediaItemCount > 0) {
            controller.getMediaItemAt(0).mediaId
        } else null

        val firstIdInNewList = surahs.firstOrNull()?.number

        // If the list is different, replace the whole playlist
        if (firstIdInQueue != firstIdInNewList) {
            controller.stop() // Stop current playback
            controller.setMediaItems(surahs.toMediaItems(recitationId))
            controller.prepare()
        }

        // Now find the index of the specific surah in the (now updated) list
        val index = (0 until controller.mediaItemCount).firstOrNull {
            controller.getMediaItemAt(it).mediaId == targetSurahNumber
        }

        index?.let {
            controller.seekTo(it, 0L)
            controller.play()
        }
    }

    fun List<Surah>.toMediaItems(recitationId: String): List<MediaItem> = map { surah ->

        val metaData = MediaMetadata.Builder()
            .setTitle(surah.englishName)
            .setArtworkUri(null)
            .build()

        MediaItem.Builder()
            .setUri(BuildConfig.AUDIO_BASE_URL.format(recitationId, surah.number))
            .setMediaId("${surah.number}.$recitationId")
            .setMediaMetadata(metaData)
            .setTag(surah.number)
            .build()
    }

    fun releaseFuture() {
        MediaController.releaseFuture(controllerFuture)
        _controller.value = null
    }
}
