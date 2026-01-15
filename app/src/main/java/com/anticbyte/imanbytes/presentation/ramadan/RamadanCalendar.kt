package com.anticbyte.imanbytes.presentation.ramadan

import RamadanDayDetailSheet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.LocalExtendedColors

@Composable
fun RamadanCalendarRoute(
    modifier: Modifier = Modifier,
    viewModel: RamadanDayDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    RamadanCalendarScreen(
        modifier = modifier,
        state = state,
        onNavigateUp = navigateUp
    )
}

@Composable
fun RamadanCalendarScreen(
    modifier: Modifier = Modifier,
    state: RamadanDayDetailScreenState,
    onNavigateUp: () -> Unit = {}
) {
    val extendedColors = LocalExtendedColors.current
    var isSheetExpanded by rememberSaveable { mutableStateOf(false) }
    var ramadanCalender by remember { mutableStateOf(RamadanCalender()) }
    val dayGroups = remember(state.monthPrayerTime) {
        state.monthPrayerTime.chunked(10)
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Calendar",
                subtitle = "Ramadan calender 2026",
                isBackVisible = true,
                onNavigationIconClick = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(bottom = 88.dp)
    ) { innerPadding ->
        RamadanDayDetailSheet(
            day = ramadanCalender, showSheet = isSheetExpanded,
            onDismiss = { isSheetExpanded = !isSheetExpanded })
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 48.dp
                ),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
            ) {
                dayGroups.fastForEachIndexed { index, group ->
                    item {
                        Text(
                            "10 Days of ${
                                when (index) {
                                    0 -> "Mercy"
                                    1 -> "Forgiveness"
                                    else -> "Success"
                                }
                            }".uppercase(),
                            style = typography.titleSmall,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(vertical = 12.dp),
                            textAlign = TextAlign.Center,
                            color = colorScheme.primary

                        )
                    }
                    itemsIndexed(group) { index, ramadan ->
                        val isHoliday = ramadan.holidays.isNotEmpty()
                        SegmentedListItem(
                            onClick = {
                                ramadanCalender = ramadan
                                isSheetExpanded = !isSheetExpanded
                            },
                            shapes = ListItemDefaults.segmentedShapes(
                                index,
                                group.size
                            ),
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = colorScheme.secondaryContainer,
                                            shapes.large
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        ramadan.hijriDay,
                                        style = typography.titleMedium,
                                        color = colorScheme.secondary
                                    )
                                }
                            },
                            supportingContent = {
                                Text(ramadan.gregorianDate)
                            },
                            overlineContent = {
                                Text(ramadan.hijriDate)
                            },
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = if (isHoliday) colorScheme.surfaceContainerHighest else colorScheme.surfaceContainer,
                            ),
                        ) {
                            Text(
                                ramadan.gregorianWeekday.take(3),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RamadanCalendarDetailSheet(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onDismiss: () -> Unit = {},
    ramadanCalender: RamadanCalender
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    LaunchedEffect(isExpanded) {
        if (isExpanded) sheetState.show() else sheetState.hide()
    }
    if (isExpanded)
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = {},
            containerColor = colorScheme.surface,
        ) {
            Scaffold(topBar = {
                AppTopBar(
                    title = ramadanCalender.hijriDate,
                    isBackVisible = true,
                    onNavigationIconClick = onDismiss,
                    subtitle = ramadanCalender.gregorianDate
                )
            }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                    ) {
                        itemsIndexed(ramadanCalender.prayerTimes) { index, prayer ->
                            SegmentedListItem(
                                onClick = {},
                                shapes = ListItemDefaults.segmentedShapes(
                                    index,
                                    ramadanCalender.prayerTimes.size
                                ),
                                leadingContent = {

                                },
                                supportingContent = {
                                    Text(prayer.second)
                                },
                                colors = ListItemDefaults.segmentedColors(containerColor = colorScheme.surfaceContainer)
                            ) {
                                Text(prayer.first)
                            }
                        }
                    }
                }
            }
        }
}

class StateProvider : PreviewParameterProvider<RamadanDayDetailScreenState> {
    override val values = sequenceOf(
        RamadanDayDetailScreenState(
            monthPrayerTime = listOf(
                RamadanCalender(
                    gregorianDate = "18 Feb 2026",
                    hijriDate = "1 Ramadan 1447",
                    prayerTimes = listOf(
                        "Imsak" to "05:04",
                        "Fajr" to "05:34",
                        "Sunrise" to "06:30",
                    ),
                    holidays = listOf("Eid-ul-Fitr"),
                    hijriDay = "1",
                    gregorianWeekday = "Tuesday",
                    sunset = "6:04 PM",
                    imsak = "4:04 AM"
                ),
                RamadanCalender(
                    gregorianDate = "19 Feb 2026",
                    hijriDate = "2 Ramadan 1447",
                    prayerTimes = listOf(
                        "Imsak" to "05:04",
                        "Fajr" to "05:34",
                        "Sunrise" to "06:30",
                    ),
                    hijriDay = "2",
                    gregorianWeekday = "Wednesday",
                )
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun RamadanCalendarPreview(
    @PreviewParameter(StateProvider::class) state: RamadanDayDetailScreenState
) {
    ImanBytesTheme(dynamicColor = false, darkTheme = true) {
        RamadanCalendarScreen(
            state = state
        )
    }
}