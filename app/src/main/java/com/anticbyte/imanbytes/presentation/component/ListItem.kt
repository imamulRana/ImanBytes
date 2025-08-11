package com.anticbyte.imanbytes.presentation.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun KnowledgeSectionItem(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int = R.drawable.ic_book_fill_sharp,
    @StringRes titleRes: Int = R.string.title_quran,
    @StringRes descriptionRes: Int = R.string.desc_quran,
    onItemClick: () -> Unit = {}
) {
    ImanBytesTheme(darkTheme = true) {
        ElevatedCard(
            onClick = onItemClick,
            modifier = modifier
                .aspectRatio(ratio = 1f),
            shape = shapes.extraLarge,
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = ImageVector.vectorResource(id = leadingIcon), null)
                    Text(
                        text = stringResource(id = titleRes),
                        style = typography.titleLarge
                    )
                }
                Text(
                    text = stringResource(id = descriptionRes),
                    style = typography.labelLarge,
                    color = Color(0xFFAAAAAA)
                )
            }
        }
    }
}

@Composable
fun AudioPlayerInfoItem(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    infoTitle: String = "",
    infoDescription: String = "",
    onPlayPauseClick: () -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable {
                onItemClick()
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedIconButton(onClick = onPlayPauseClick) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isPlaying) R.drawable.pause_24px else R.drawable.play_arrow_24px
                ),
                contentDescription = "Play Audio"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = infoTitle, style = typography.titleMedium)
            Text(text = infoDescription, style = typography.bodySmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
            contentDescription = "Navigate to player"
        )
    }
}

@Composable
fun QuranListItem(
    modifier: Modifier = Modifier,
    leading: String = "",
    heading: String = "",
    description: String = "",
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = colorScheme.surfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = leading)
            }
        }, headlineContent = {
            Text(text = heading)
        }, supportingContent = {
            Text(text = description)
        })
}


@Composable
fun AudioPlayerSmall(modifier: Modifier = Modifier) {
    ElevatedCard(
        onClick = {
            //todo apply on click
        },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconButton(
                onClick = {
                    //todo apply on click
                },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.play_arrow_24px),
                    contentDescription = "Play/Pause",
                )
            }
            Column {
                //todo change to dynamic string
                Text("Surah")
                Text("Verse")
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
                contentDescription = "Navigate"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopicCardPreview() {
    ImanBytesTheme(darkTheme = true, dynamicColor = false) {
        AudioPlayerSmall()
    }
}