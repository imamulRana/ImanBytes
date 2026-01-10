package com.anticbyte.imanbytes.presentation.ramadan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun RamadanDayDetailRoute(
    viewModel: RamadanDayDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RamadanDayDetailScreen(
        uiState = uiState
    )
}

@Composable
fun RamadanDayDetailScreen(
    uiState: RamadanDayDetailScreenState
) {
    Scaffold(topBar = {
        AppTopBar(
            title = uiState.monthPrayerTime.month.plus(uiState.monthPrayerTime.year),
            subtitle = uiState.monthPrayerTime.date,
            isBackVisible = true,
            onNavigationIconClick = {}
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
            ) {
                suhoorAndIftarSection("5:14", "6:15")
                ramadanPrayerTimes(
                    uiState.monthPrayerTime.prayerTimes
                )
            }
        }
    }
}

fun PrayerTime.toTimeList(): List<Pair<String, String>> = listOf(
    "Fajr" to fajr,
    "Dhuhr" to dhuhr,
    "Asr" to asr,
    "Maghrib" to maghrib,
    "Isha" to isha
)

fun LazyListScope.suhoorAndIftarSection(
    suhoorTime: String,
    iftaarTime: String
) {
    item {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SuhoorAndIftaarSectionItem(modifier = Modifier.weight(1f))
            SuhoorAndIftaarSectionItem(modifier = Modifier.weight(1f))
        }
    }
}


fun LazyListScope.ramadanPrayerTimes(prayerTime: List<Pair<String, String>>) {
    itemsIndexed(prayerTime) { index, (prayer, time) ->
        SegmentedListItem(
            onClick = {},
            shapes = ListItemDefaults.segmentedShapes(index, prayerTime.size),
            supportingContent = {
                Text(time)
            }, colors = ListItemDefaults.segmentedColors(colorScheme.surfaceContainerLow)
        ) {
            Text(prayer)
        }
    }
}

@Composable
fun SuhoorAndIftaarSectionItem(modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        onClick = {}
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = colorScheme.secondaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_fajr), null)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Suhoor ends", style = typography.labelSmall)
                Text("5:53 AM", style = typography.titleLarge)
            }
        }
    }
}

@Preview
@Composable
private fun SuhoorAndIftaarSectionItemPreview() {
    ImanBytesTheme {
        SuhoorAndIftaarSectionItem()
    }
}

@Composable
fun RamadanTitle(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
private fun RamadanDayDetailScreenPreview() {
    ImanBytesTheme {
        RamadanDayDetailScreen(
            uiState = RamadanDayDetailScreenState()
        )
    }
}
