package com.anticbyte.imanbytes.feature

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AudioPlaybackController @Inject constructor(
    @ApplicationContext val context: Context,
    private val sessionToken: SessionToken
) {
    val mediaController: MediaController?
        get() = _mediaControllerFlow.value

    private val _mediaControllerFlow = MutableStateFlow<MediaController?>(null)
    val mediaControllerFlow = _mediaControllerFlow.asStateFlow()
    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.PlayerIdle)
    val playerStateFlow = _playerState.asStateFlow()


    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    init {
        initController()
    }

    private fun initController() {
        // The sessionToken is now injected, no need to create it here.
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                // When the connection is successful, update the StateFlow
                _mediaControllerFlow.value = controllerFuture.get().apply {
                    prepare()
                }
                observePlayerState()
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    private fun observePlayerState() {
        val listener = object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                _playerState.value = when {
                    player.isPlaying -> PlayerState.PlayerPlaying
                    player.currentMediaItem == null -> PlayerState.PlayerIdle
                    player.isLoading -> PlayerState.PlayerLoading
                    else -> PlayerState.PlayerPaused
                }
                _currentIndex.value = player.currentMediaItemIndex
            }
        }
        mediaController?.addListener(listener)
    }

    fun setMediaItem(recitationType: RecitationType) {
        val items = (0..114).map { surahNumber ->
            val uri = BuildConfig.AUDIO_BASE_URL.format(
                recitationType.recitationId,
                surahNumber
            )
            MediaItem.fromUri(uri)
        }

        mediaController?.setMediaItems(items)
    }
}