package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(UnstableApi::class)
@Composable
fun RecitationListItem(
    modifier: Modifier = Modifier,
    surah: Surah,
    nowPlayingItem: String = "",
    onPlaySurah: (surahNumber: String) -> Unit = {},
    shapes: ListItemShapes
) {
    SegmentedListItem(
        selected = nowPlayingItem == surah.number,
        shapes = shapes,
        onClick = { onPlaySurah(surah.number) },
        modifier = modifier,
        leadingContent = {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.secondaryContainer, CircleShape)
                    .size(size = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.number,
                    color = colorScheme.onSecondaryContainer,
                    style = typography.labelLarge
                )
            }
        },
        overlineContent = { Text(surah.name) },
        supportingContent = { Text(surah.englishNameTranslation) },
        trailingContent = {
            Text(text = "${surah.numberOfAyahs} Ayahs")
            /*
                        Column(horizontalAlignment = Alignment.End) {
                            Spacer(Modifier.size(48.dp))
                            FilledIconToggleButton(
                                modifier = Modifier.size(
                                    IconButtonDefaults.smallContainerSize(
                                        widthOption = IconButtonDefaults.IconButtonWidthOption.Uniform
                                    )
                                ),
                                checked = isCurrentlyPlaying,
                                onCheckedChange = { togglePlayPause(surah.number) },
                                shapes = IconButtonDefaults.toggleableShapes(),
                                colors = IconButtonDefaults.iconToggleButtonColors(
                                    containerColor = colorScheme.primaryContainer,
                                    checkedContainerColor = colorScheme.primaryContainer,
                                    contentColor = colorScheme.onPrimaryContainer,
                                    checkedContentColor = colorScheme.onPrimaryContainer
                                )
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        id = if (isCurrentlyPlaying) R.drawable.pause_24px
                                        else R.drawable.play_arrow_24px
                                    ),
                                    contentDescription = null,
                                )
                            }
                        }
            */
        },
        colors = ListItemDefaults.segmentedColors(
            containerColor = colorScheme.surfaceContainer,
            leadingContentColor = colorScheme.primaryContainer,
            selectedContainerColor = colorScheme.surfaceVariant
        )
    ) {
        Text(surah.englishName)
    }
}

@OptIn(UnstableApi::class)
@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
    }
}