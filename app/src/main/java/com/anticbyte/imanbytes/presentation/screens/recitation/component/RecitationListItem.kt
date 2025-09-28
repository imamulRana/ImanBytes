package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState

@Composable
fun RecitationListItem(
    modifier: Modifier = Modifier,
    surah: Surah,
    playerState: PlayerState = PlayerState.PlayerIdle,
    onItemClick: (surahNumber: String) -> Unit = {},
    playSurah: (surahNumber: String) -> Unit,
) {
    val isPlaying = playerState is PlayerState.PlayerPlaying
    ListItem(
        modifier = modifier
            .clickable(onClick = { onItemClick(surah.number) }), leadingContent = {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.secondaryContainer, CircleShape)
                    .size(size = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.number,
                    color = colorScheme.onSecondaryContainer,
                    style = typography.titleMedium
                )
            }
        },
        headlineContent = { Text(surah.englishName) },
        overlineContent = { Text(surah.name) },
        supportingContent = { Text(surah.englishNameTranslation) },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${surah.numberOfAyahs} Ayahs")
                Spacer(Modifier.size(48.dp))
                FilledIconToggleButton(
                    modifier = Modifier.size(
                        IconButtonDefaults.smallContainerSize(
                            widthOption = IconButtonDefaults.IconButtonWidthOption.Wide
                        )
                    ),
                    checked = isPlaying,
                    onCheckedChange = { playSurah(surah.number) },
                    shapes = IconButtonDefaults.toggleableShapes()
                ) {
                    if ((playerState is PlayerState.PlayerLoading || playerState is PlayerState.PlayerBuffering))
                        LoadingIndicator()
                    else
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (isPlaying) R.drawable.pause_24px else R.drawable.play_arrow_24px
                            ),
                            contentDescription = null,
                        )
                }
            }
        },
        colors = ListItemDefaults.colors(
            headlineColor = if (isPlaying) {
                colorScheme.primary
            } else ListItemDefaults.contentColor
        )
    )
}