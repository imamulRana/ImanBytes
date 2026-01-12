package com.anticbyte.imanbytes.presentation.ramadan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun RamadanList(
    modifier: Modifier = Modifier,
    ramadanCalender: List<RamadanCalender>
) {
    Scaffold(topBar = {
        AppTopBar(
            title = "Data 1447", subtitle = "h",
            isBackVisible = true,
            onNavigationIconClick = {})
    }) { innerPadding ->
        // List content will go here
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp), contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            itemsIndexed(ramadanCalender) { index, calender ->
                SegmentedListItem(
                    onClick = {},
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = colorScheme.primaryContainer,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(calender.hijri.substringBefore(" "))
                        }
                    },
                    shapes = ListItemDefaults.segmentedShapes(index, ramadanCalender.size),
                    colors = ListItemDefaults.segmentedColors(containerColor = colorScheme.surfaceContainerLowest),
                    trailingContent = {
                        Column {
                            Text(calender.prayerTimes[4].second, style = typography.titleLarge)
                            Text(calender.prayerTimes[5].first, style = typography.labelSmall)
                        }
                    }
                ) {
                    Text(calender.date)
                }

            }

        }
    }
}

@Preview
@Composable
private fun RamadanListPreview() {
    val dummyRamadanData = List(30) { index ->
        RamadanCalender(
            date = "${index + 1} March",
            day = when (index % 7) {
                0 -> "Saturday"
                1 -> "Sunday"
                2 -> "Monday"
                3 -> "Tuesday"
                4 -> "Wednesday"
                5 -> "Thursday"
                else -> "Friday"
            },
            designation = "AH",
            hijri = "${index + 1} Date 1446",
            gregorian = "${index + 1}-03-2025",
            meta = "Asia/Dhaka",
            method = "University of Islamic Sciences, Karachi",
            month = "Ramadan",
            year = "1446",
            holidays = if (index == 29) listOf("Eid-ul-Fitr") else emptyList(),
            prayerTimes = listOf(
                "Imsak" to "04:45",
                "Fajr" to "04:55",
                "Sunrise" to "06:10",
                "Dhuhr" to "12:15",
                "Asr" to "15:30",
                "Maghrib" to "18:15",
                "Sunset" to "18:15",
                "Isha" to "19:30",
                "Midnight" to "00:00"
            ),
            midnight = "00:00"
        )
    }

    ImanBytesTheme {
        RamadanList(ramadanCalender = dummyRamadanData)
    }
}
