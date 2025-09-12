package com.anticbyte.imanbytes.presentation.screens.recitation.component

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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationPlayBack(
    modifier: Modifier = Modifier,
    audioTimeLine: Pair<Long, Long> = Pair(11, 12),
    isPlaying: Boolean = false,
    onPlayPause: () -> Unit = {},
    onSeekForward: () -> Unit = {},
    onSeekBackward: () -> Unit = {},
    progress: () -> Float = { 0f }
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LinearWavyProgressIndicator(
                progress, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = audioTimeLine.first.toTimeLine(),
                    style = typography.labelSmall
                )
                Text(
                    text = audioTimeLine.second.toTimeLine(),
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
            SeekAudio(onClick = onSeekBackward, seekType = PlayerSeekType.BACKWARD)
            PlayPauseAudio(isPlaying = isPlaying, onClick = onPlayPause)
            SeekAudio(onClick = onSeekForward, seekType = PlayerSeekType.FORWARD)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SeekAudio(modifier: Modifier = Modifier, onClick: () -> Unit, seekType: PlayerSeekType) {
    IconButton(
        onClick = onClick,
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayPauseAudio(
    modifier: Modifier = Modifier, isPlaying: Boolean, onClick: () -> Unit
) {
    FilledIconButton(
        onClick = onClick,
        shapes = IconButtonDefaults.shapes(
            shape = IconButtonDefaults.largeSquareShape
        ),
        modifier = modifier.size(IconButtonDefaults.largeContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = if (isPlaying) R.drawable.pause_24px
                else R.drawable.play_arrow_24px
            ), contentDescription = null, modifier = modifier.size(IconButtonDefaults.largeIconSize)
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
            RecitationPlayBack(modifier = Modifier)
        }
    }
}