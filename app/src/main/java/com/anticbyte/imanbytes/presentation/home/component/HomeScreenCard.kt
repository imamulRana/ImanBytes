package com.anticbyte.imanbytes.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun HomeScreenCard(
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit = {},
    subtitle: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
    metaText: @Composable () -> Unit = {},
    metaAction: () -> Unit = {},
    extraContent: (@Composable () -> Unit)? = null
) {
    ElevatedCard(modifier = modifier, onClick = {}, shape = shapes.extraLarge) {
        ListItem(
            modifier = Modifier.padding(top = 8.dp),
            leadingContent = leadingContent,
            supportingContent = subtitle,
            trailingContent = trailingContent,
            headlineContent = title,
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
        content?.let {
            ListItem(
                headlineContent = it,
                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
            )
        }
        if (extraContent != null) extraContent()
        ListItem(
            headlineContent = metaText,
            trailingContent = {
                FilledTonalIconButton(onClick = metaAction) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill), null)
                }
            },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
    }
}

@Composable
fun ListItemAvatar(
    modifier: Modifier = Modifier,
    label: String = ""
) {
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 40.dp, minHeight = 40.dp)
            .background(color = colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(label)
    }
}

enum class PrayerTimeSec(
    @DrawableRes val icon: Int,
    val color: Color
) {
    FAJR(
        R.drawable.sunny_snowing_24px,
        Color(0xFF5B6B8F)
    ),
    DHUHR(
        R.drawable.sunny_24px,
        Color(0xFFC68A3D)
    ),
    ASR(
        R.drawable.partly_cloudy_day_24px,
        Color(0xFFD68847)
    ),
    MAGHRIB(
        R.drawable.wb_twilight_24px,
        Color(0xFFB5485C)
    ),
    ISHA(
        R.drawable.bedtime_24px,
        Color(0xFF4A5272)
    )
}

@Composable
fun PrayerTimeSection(
    modifier: Modifier = Modifier,
    prayerTime: List<Pair<String, String>> = emptyList()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
    ) {
        Text(
            text = "Prayer Times".uppercase(),
            modifier = Modifier.padding(8.dp),
            style = typography.labelSmall
        )
        prayerTime.fastForEachIndexed { index, (prayer, time) ->
            SegmentedListItem(
                onClick = {},
                leadingContent = {
                    Icon(
                        ImageVector.vectorResource(PrayerTimeSec.entries[index].icon),
                        null,
                    )
                },
                shapes = ListItemDefaults.segmentedShapes(index, prayerTime.size),
                trailingContent = {
                    Text(time)
                }
            ) {
                Text(prayer)
            }
        }
    }
}

@Composable
fun ComposableWithTitle(
    modifier: Modifier = Modifier,
    title: String = "",
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
    ) {
        Text(
            text = title.uppercase(),
            modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            style = typography.labelSmall
        )
        content()
    }
}

@Preview
@Composable
private fun PrayerTimeSectionPreview() {
    ImanBytesTheme {
        PrayerTimeSection(
            prayerTime = listOf(
                "Fajr" to "04:30",
            )
        )
    }
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        HomeScreenCard(
            title = {
                Text("The bee")
            }
        )
    }
}
