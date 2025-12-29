package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(UnstableApi::class)
@Composable
fun RecitationListItem(
    modifier: Modifier = Modifier,
    surah: Surah,
    onSurahClick: (surahNumber: String) -> Unit = {},
    player: Player,
    shapes: ListItemShapes
) {
    val playerState = rememberPlayPauseButtonState(player)
    val isPlaying = player.isPlaying
    SegmentedListItem(
        selected = player.currentMediaItem?.mediaId == surah.number,
        shapes = shapes,
        onClick = { onSurahClick(surah.number) },
        modifier = modifier,
        leadingContent = {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.primaryContainer, CircleShape)
                    .size(size = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.number,
                    color = colorScheme.onSurfaceVariant,
                    style = typography.labelLarge
                )
            }
        },
        overlineContent = { Text(surah.name) },
        supportingContent = { Text(surah.englishNameTranslation) },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${surah.numberOfAyahs} Ayahs")
                Spacer(Modifier.size(48.dp))
                FilledIconToggleButton(
                    modifier = Modifier.size(
                        IconButtonDefaults.smallContainerSize(
                            widthOption = IconButtonDefaults.IconButtonWidthOption.Uniform
                        )
                    ),
                    checked = player.isPlaying && player.currentMediaItem?.mediaId == surah.number,
                    onCheckedChange = {
                        if (!player.isPlaying) {
                            player.setMediaItems(List(114) {
                                MediaItem.Builder()
                                    .setMediaId(surah.number)
                                    .setUri(
                                        BuildConfig.AUDIO_BASE_URL.format(
                                            "ar.alafasy", surah.number
                                        )
                                    ).build()
                            })
                            player.prepare()
                            player.play()
                        } else
                            player.pause()
                    },
                    shapes = IconButtonDefaults.toggleableShapes(),
                    colors = IconButtonDefaults.iconToggleButtonColors(
                        containerColor = colorScheme.primaryContainer,
                        checkedContainerColor = colorScheme.primaryContainer
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (
                                player.isPlaying && player.currentMediaItem?.mediaId == surah.number
                            ) R.drawable.pause_24px else R.drawable.play_arrow_24px
                        ),
                        contentDescription = null,
                    )
                }
            }
        },
        colors = ListItemDefaults.segmentedColors(
            containerColor = colorScheme.surfaceContainerLow,
            leadingContentColor = colorScheme.primaryContainer,
            selectedContainerColor = colorScheme.surfaceVariant
        )
    ) {
        Text(surah.englishName)
    }
}

class PrevS : CollectionPreviewParameterProvider<String>(listOf())

@OptIn(UnstableApi::class)
@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
    }
}