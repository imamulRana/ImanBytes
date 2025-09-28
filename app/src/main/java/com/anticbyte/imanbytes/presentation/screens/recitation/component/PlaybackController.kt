package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Immutable
data class RecitationPlayBackState(
    val surahNumber: String = "",
    val currentTime: Long = 0L,
    val duration: Long = 0L,
    val playerState: PlayerState = PlayerState.PlayerIdle,
    val progress: Float = 0F
)

interface RecitationPlayBackActions {
    fun onPlayPause(surahNumber: String)
    fun onSeekForward()
    fun onSeekBackward()
    fun onSeek(seekTo: Float)
}

val sampleActions = object : RecitationPlayBackActions {
    override fun onPlayPause(surahNumber: String) {}
    override fun onSeekForward() {}
    override fun onSeekBackward() {}
    override fun onSeek(seekTo: Float) {}
}

@Composable
fun RecitationPlayBack(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState = RecitationPlayBackState(),
    actions: RecitationPlayBackActions = sampleActions
) {
    var sliderWidth by remember { mutableFloatStateOf(0f) }
    var dragPosition by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(playBackState.progress) {
        if (!isDragging) dragPosition = playBackState.progress
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LinearWavyProgressIndicator(
                progress = { dragPosition },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .onGloballyPositioned {
                        sliderWidth = it.size.width.toFloat()
                    }
                    .pointerInput(Unit) {
                        // Handle tap gestures for immediate seeking.
                        detectTapGestures(onTap = { offset ->
                            val newProgress = (offset.x / sliderWidth).coerceIn(0f, 1f)
                            // Notify the caller to seek the player.
                            actions.onSeek(newProgress)
                        })
                    }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
//                          During a drag, update our local state directly. This is fast and smooth.
                            dragPosition = (dragPosition + (delta / sliderWidth)).coerceIn(0f, 1f)
                        },
                        onDragStarted = {
                            isDragging = true
                        },
                        onDragStopped = {
                            // When dragging stops, notify the caller to seek the player.
                            actions.onSeek(dragPosition)
                            isDragging = false
                        }
                    )
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = playBackState.currentTime.toTimeLine(),
                    style = typography.labelSmall
                )
                Text(
                    text = playBackState.duration.toTimeLine(),
                    style = typography.labelSmall
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            SeekAudio(
                actions = actions,
                playBackState = playBackState,
                seekType = PlayerSeekType.BACKWARD
            )
            PlayPauseAudio(
                playerState = PlayerState.PlayerLoading,
                playBackState = playBackState,
                actions = actions
            )
            SeekAudio(
                actions = actions,
                playBackState = playBackState,
                seekType = PlayerSeekType.FORWARD
            )
        }
    }
}

@Composable
fun SeekAudio(
    modifier: Modifier = Modifier,
    actions: RecitationPlayBackActions,
    seekType: PlayerSeekType,
    playBackState: RecitationPlayBackState
) {
    IconButton(
        onClick = { actions.onPlayPause(playBackState.surahNumber) },
        shapes = IconButtonDefaults.shapes(
            shape = IconButtonDefaults.mediumSquareShape
        ),
        modifier = modifier.size(IconButtonDefaults.mediumContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = if (seekType.name == PlayerSeekType.FORWARD.name) R.drawable.ic_forward_10
                else R.drawable.ic_replay_10
            ), contentDescription = null, modifier = modifier.size(IconButtonDefaults.largeIconSize)
        )
    }
}

@Composable
fun PlayPauseAudio(
    modifier: Modifier = Modifier, playerState: PlayerState,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlayBackActions
) {
    FilledIconButton(
        onClick = { actions.onPlayPause(playBackState.surahNumber) },
        shapes = IconButtonDefaults.shapes(
            shape = IconButtonDefaults.largeSquareShape
        ),
        modifier = modifier.size(IconButtonDefaults.largeContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)),
    ) {
        if (playerState is PlayerState.PlayerLoading)
            LoadingIndicator(color = colorScheme.onPrimary)
        else
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if ((playerState is PlayerState.PlayerPlaying) or
                        (playerState is PlayerState.PlayerIdle)
                    ) R.drawable.pause_24px
                    else R.drawable.play_arrow_24px
                ),
                contentDescription = null,
                modifier = modifier.size(IconButtonDefaults.largeIconSize)
            )
    }
}
enum class PlayerSeekType(val seekDuration: Long) {
    FORWARD(seekDuration = 10000L),
    BACKWARD(seekDuration = 10000L)
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        Scaffold {
            it
            var state by remember { mutableFloatStateOf(0f) }
            RecitationPlayBack(
                modifier = Modifier,
                playBackState = RecitationPlayBackState(
                    surahNumber = "",
                    2023L, 23249L,
                    PlayerState.PlayerLoading,
                    progress = state
                ),
                actions = object : RecitationPlayBackActions {
                    override fun onPlayPause(surahNumber: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onSeekForward() {
                        TODO("Not yet implemented")
                    }

                    override fun onSeekBackward() {
                        TODO("Not yet implemented")
                    }

                    override fun onSeek(seekTo: Float) {
                        state = seekTo
                    }

                }
            )
        }
    }
}