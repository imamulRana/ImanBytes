package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState
import com.anticbyte.imanbytes.theme.ImanBytesTheme

data class RecitationPlayBackState(
    val surahNumber: String = "",
    val currentTime: Long = 0L,
    val duration: Long = 0L,
    val playerState: PlayerState = PlayerState.PlayerIdle,
    val progress: Float = 0F
)

data class RecitationPlaybackAction(
    val playPause: (surahNumber: String) -> Unit = {},
    val seekForward: () -> Unit = {},
    val seekBackward: () -> Unit = {},
    val seek: (Float) -> Unit = {},
    val persistCurrentSurah: (String?) -> Unit = {},
    val onSurahClick: (String) -> Unit = {}
)

@Composable
fun RecitationPlayBack(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlaybackAction = RecitationPlaybackAction()
) {
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
            Slider(
                value = dragPosition, onValueChange = { newValue ->
                    isDragging = true
                    dragPosition = newValue
                },
                onValueChangeFinished = {
                    isDragging = false
                    actions.seek(dragPosition)
                }, track = {
                    LinearWavyProgressIndicator(
                        progress = { dragPosition },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                thumb = {
                    SliderDefaults.Thumb(thumbSize = DpSize(10.dp,15.dp), interactionSource = MutableInteractionSource())
                })
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
                onSeek = actions.seekBackward,
                seekType = PlayerSeekType.BACKWARD
            )
            PlayPauseAudio(
                playBackState = playBackState,
                actions = actions
            )
            SeekAudio(
                onSeek = actions.seekForward,
                seekType = PlayerSeekType.FORWARD
            )
        }
    }
}

@Composable
fun SeekAudio(
    modifier: Modifier = Modifier,
    onSeek: () -> Unit,
    seekType: PlayerSeekType,
) {
    IconButton(
        onClick = onSeek,
        shapes = IconButtonDefaults.shapes(
            shape = IconButtonDefaults.mediumSquareShape
        ),
        modifier = modifier.size(IconButtonDefaults.mediumContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = if (seekType == PlayerSeekType.FORWARD) R.drawable.ic_forward_10
                else R.drawable.ic_replay_10
            ), contentDescription = null, modifier = modifier.size(IconButtonDefaults.largeIconSize)
        )
    }
}

@Composable
fun PlayPauseAudio(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlaybackAction = RecitationPlaybackAction()
) {
    FilledIconButton(
        onClick = { actions.playPause(playBackState.surahNumber) },
        shapes = IconButtonDefaults.shapes(
            shape = IconButtonDefaults.largeSquareShape
        ),
        modifier = modifier.size(IconButtonDefaults.largeContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)),
    ) {
        if (playBackState.playerState is PlayerState.PlayerLoading)
            LoadingIndicator(color = colorScheme.onPrimary)
        else
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (playBackState.playerState is PlayerState.PlayerPlaying) R.drawable.pause_24px
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
            )
        }
    }
}