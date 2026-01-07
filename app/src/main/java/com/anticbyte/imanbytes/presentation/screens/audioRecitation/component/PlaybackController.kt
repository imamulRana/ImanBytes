package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util.getStringForTime
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import androidx.media3.ui.compose.state.rememberProgressStateWithTickInterval
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.theme.ImanBytesTheme

enum class PlayerSeekType(val seekDuration: Long) {
    FORWARD(seekDuration = 10000L), BACKWARD(seekDuration = 10000L)
}

@OptIn(UnstableApi::class)
@Composable
fun AudioControlSection(
    modifier: Modifier = Modifier,
    player: Player? = null
) {
    if (player == null) return
    val playPauseState = rememberPlayPauseButtonState(player = player)
    val progressState =
        rememberProgressStateWithTickInterval(player = player, tickIntervalMs = 1000L)
    val current = getStringForTime(progressState.currentPositionMs)
    val duration by remember { derivedStateOf { getStringForTime(progressState.durationMs) } }

    var dragPosition by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(progressState.currentPositionMs) {
        if (!isDragging) dragPosition =
            (progressState.currentPositionMs.toFloat() / progressState.durationMs.toFloat())
                .coerceIn(0f, 1f)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Slider(value = dragPosition, onValueChange = { newValue ->
                isDragging = true
                dragPosition = newValue
            }, onValueChangeFinished = {
                isDragging = false
                player.seekTo((player.duration * dragPosition).toLong())
            })
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = current,
                    style = typography.labelSmall
                )
                Text(
                    text = duration,
                    style = typography.labelSmall
                )
            }
        }
        AudioControlButtons(
            playerActions = listOf(
                {
                    IconButton(
                        onClick = player::seekBack,
                        shapes = IconButtonDefaults.shapes(),
                        modifier = Modifier.size(
                            IconButtonDefaults.mediumContainerSize()
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(IconButtonDefaults.mediumIconSize),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_replay_10),
                            contentDescription = null
                        )
                    }
                },
                {
                    FilledIconButton(
                        onClick = playPauseState::onClick,
                        shapes = IconButtonDefaults.shapes(
                            shape = IconButtonDefaults.largeSquareShape
                        ),
                        modifier = Modifier.size(
                            IconButtonDefaults.largeContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(IconButtonDefaults.largeIconSize),
                            imageVector = ImageVector.vectorResource(if (!playPauseState.showPlay) R.drawable.ic_pause_fill else R.drawable.ic_play_arrow_fill),
                            contentDescription = null
                        )
                    }
                },
                {
                    IconButton(
                        onClick = player::seekForward,
                        shapes = IconButtonDefaults.shapes(),
                        modifier = Modifier.size(
                            IconButtonDefaults.mediumContainerSize()
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(IconButtonDefaults.mediumIconSize),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_forward_10),
                            contentDescription = null
                        )
                    }
                }
            )
        )
    }
}

@Composable
fun AudioControlButtons(
    modifier: Modifier = Modifier,
    playerActions: List<@Composable () -> Unit>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        playerActions.forEach { composable ->
            composable()
        }
    }
}

@OptIn(UnstableApi::class)
@Preview(showBackground = true)
@Composable
private fun DefPrev() {
    ImanBytesTheme(dynamicColor = false) {
//        AudioControlSection()
    }
}