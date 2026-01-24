package com.anticbyte.imanbytes.presentation.player

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TonalToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.feature.PlayBackState
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(UnstableApi::class)
@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    playBackState: PlayBackState,
    onShowSheet: () -> Unit = {},
    onDismissSheet: () -> Unit = {},
    player: Player
) {
    val playPauseButtonState = rememberPlayPauseButtonState(player)
    if (!playBackState.isControllerReady || playBackState.currentMediaId == null) return
    SegmentedListItem(
        modifier = modifier,
        onClick = onShowSheet,
        shapes = ListItemDefaults.shapes(),
        supportingContent = {
            Text(player.mediaMetadata.title.toString(), style = typography.bodySmall)
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = colorScheme.secondaryContainer,
                        shape = shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("1")
            }
        },
        trailingContent = {
            TonalToggleButton(!playPauseButtonState.showPlay, onCheckedChange = {
                playPauseButtonState.onClick()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (!playPauseButtonState.showPlay) R.drawable.ic_pause_fill else R.drawable.ic_play_arrow_fill
                    ), null
                )
            }
        }
    ) {
        Text(audioPlayer.mediaMetadata.title.toString(), style = typography.titleMedium)
    }
}
}

@Preview
@Composable
private fun MiniPlayerPreview() {
    ImanBytesTheme {
//        MiniPlayer(onShowSheet = {})
    }
}
