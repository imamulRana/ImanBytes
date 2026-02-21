package com.anticbyte.imanbytes.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun AppAvatar(
    modifier: Modifier = Modifier,
    color: Color = colorScheme.primaryContainer,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun PrayerTimeCard(
    prayerTimes: PrayerTime = PrayerTime()
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HomeTitle(label = "Today’s prayer time")
        Column(verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)) {
            prayerTimes.prayerTime.fastForEachIndexed { index, prayerTime ->
                val prayerTimeSec = PrayerTimeSec.entries[index]
                SegmentedListItem(
                    onClick = {},
                    shapes = ListItemDefaults.segmentedShapes(index, prayerTimes.prayerTime.size),
                    supportingContent = {
                        Text(prayerTimeSec.timing)
                    },
                    leadingContent = {
                        AppAvatar(color = colorScheme.primaryContainer) {
                            Icon(
                                imageVector = ImageVector.vectorResource(prayerTimeSec.icon),
                                contentDescription = null,
                                tint = colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    trailingContent = {
                        Text(prayerTime.second)
                    },
                    colors = ListItemDefaults.segmentedColors(containerColor = colorScheme.surfaceContainerLowest)
                ) {
                    Text(prayerTime.first)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrayerTimeCardPrev() {
    ImanBytesTheme {
        PrayerTimeCard(
            prayerTimes = PrayerTime(
                prayerTime = listOf(
                    "Fajr" to "04:30",
                    "Dhuhr" to "12:30",
                )
            )
        )
    }

}