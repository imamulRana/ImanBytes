package com.anticbyte.imanbytes.presentation.ramadan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar

@Composable
fun RamadanDayDetailSRoute(
    viewModel: RamadanDayDetailSViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    RamadanDayDetailSScreen(
        uiState = uiState,
        onNavigateUp = navigateUp
    )
}

@Composable
fun RamadanDayDetailSScreen(
    modifier: Modifier = Modifier,
    uiState: RamadanDayDetailSScreenState,
    onNavigateUp: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    uiState.day?.let { day ->
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                AppTopBar(
                    title = day.gregorianDate,
                    subtitle = day.hijriDate,
                    isBackVisible = true,
                    onNavigationIconClick = onNavigateUp,
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    day.holidays.fastForEachIndexed { index, holiday ->
                        item {
                            Text(
                                "Events", style = typography.labelMedium, modifier = Modifier
                                    .padding(8.dp)
                            )
                            SegmentedListItem(
                                shapes = ListItemDefaults.shapes(),
                                onClick = {},
                                trailingContent = {
                                    Text(holiday)
                                },
                                colors = ListItemDefaults.segmentedColors(
                                    containerColor = colorScheme.surfaceContainer
                                )
                            ) {

                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            ElevatedCard(modifier = Modifier.weight(1f)) {
                                Column(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 24.dp
                                    )
                                ) {
                                    Text(
                                        day.imsak,
                                        style = typography.headlineSmall
                                    )
                                    Text("Suhoor end time", style = typography.bodySmall)
                                }
                            }
                            ElevatedCard(modifier = Modifier.weight(1f)) {
                                Column(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 24.dp
                                    )
                                ) {
                                    Text(day.sunset, style = typography.headlineSmall)
                                    Text("Iftaar start time", style = typography.bodySmall)
                                }
                            }
                        }
                    }
                    itemsIndexed(day.prayerTimes) { index, prayerTime ->
                        SegmentedListItem(
                            shapes = ListItemDefaults.segmentedShapes(
                                index,
                                day.prayerTimes.size
                            ),
                            onClick = {},
                            trailingContent = {
                                Text(prayerTime.second)
                            },
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = colorScheme.surfaceContainer
                            )
                        ) {
                            Text(prayerTime.first)
                        }
                    }
                    item {
                        Text(
                            stringResource(R.string.iftaar_dua), style = typography.titleLarge.copy(
                                fontFamily = FontFamily(Font(R.font.lateef))
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DuaBottomSheet(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
private fun RamadanDayDetailSScreenPreview() {
    RamadanDayDetailSScreen(
        uiState = RamadanDayDetailSScreenState()
    )
}
