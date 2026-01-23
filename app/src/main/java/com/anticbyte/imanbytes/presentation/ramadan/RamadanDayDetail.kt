package com.anticbyte.imanbytes.presentation.ramadan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.home.generateWord
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun RamadanDayDetailRoute(
    viewModel: RamadanDayDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    RamadanDayDetailScreen(
        uiState = uiState,
        onNavigateUp = navigateUp
    )
}

@Composable
fun RamadanDayDetailScreen(
    modifier: Modifier = Modifier,
    uiState: RamadanDayDetailState,
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
                    if (day.holidays.isNotEmpty())
                        item {
                            Text(
                                "Events", style = typography.labelMedium, modifier = Modifier
                                    .padding(8.dp)
                            )
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                day.holidays.fastForEachIndexed { index, holiday ->
                                    SuggestionChip(onClick = {}, icon = {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_events),
                                            null
                                        )
                                    }, label = {
                                        Text(holiday)
                                    }, shape = shapes.largeIncreased)
                                }
                            }
                        }
                    item {
                        Surface(
//                            color = colorScheme.surfaceContainer,
                            shape = shapes.largeIncreased
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 24.dp
                                    )
                            ) {
                                Column {
                                    Text("Suhoor ends", style = typography.bodySmall)
                                    Text(day.imsak, style = typography.headlineMedium)
                                }
                                VerticalDivider(modifier = Modifier.fillMaxHeight())
                                Column {
                                    Text("Iftaar starts", style = typography.bodySmall)
                                    Text(day.sunset, style = typography.headlineMedium)
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
                        Spacer(modifier = Modifier.height(16.dp))
                        ListItem(
                            overlineContent = {
                                Text("Iftaar Dua")
                            }, headlineContent = {
                                Text(
                                    stringResource(R.string.iftaar_dua),
                                    style = typography.headlineLarge.copy(
                                        fontFamily = FontFamily(
                                            Font(R.font.lateef)
                                        )
                                    ),
                                    textAlign = TextAlign.Right
                                )
                            }, supportingContent = {
                                Text(generateWord(15))
                            })
                    }
                    item {
                        ListItem(
                            overlineContent = {
                                Text("Suhoor Dua")
                            }, headlineContent = {
                                Text(
                                    stringResource(R.string.iftaar_dua),
                                    style = typography.headlineLarge.copy(
                                        fontFamily = FontFamily(
                                            Font(R.font.lateef)
                                        )
                                    ),
                                    textAlign = TextAlign.Right
                                )
                            }, supportingContent = {
                                Text(generateWord(15))
                            })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TitleWithDua(modifier: Modifier = Modifier) {
    Column {
        ListItem(
            overlineContent = {
                Text("Iftaar Dua")
            }, headlineContent = {
                Text(
                    stringResource(R.string.iftaar_dua), style = typography.headlineLarge.copy(
                        fontFamily = FontFamily(
                            Font(R.font.lateef)
                        )
                    ), textAlign = TextAlign.Right
                )
            }, supportingContent = {
                Text(generateWord(15))
            })
    }
}

@Composable
fun DuaBottomSheet(
    modifier: Modifier = Modifier,
    duaSheet: List<String>
) {
    ModalBottomSheet(
        modifier = modifier, onDismissRequest = {}) {
        duaSheet.fastForEachIndexed { index, dua ->
            SegmentedListItem(
                onClick = {},
                shapes = ListItemDefaults.segmentedShapes(index, duaSheet.size)
            ) {
                Text(dua)
            }
        }
    }
}

class DuaBottomSheetState : PreviewParameterProvider<List<String>> {
    override val values: Sequence<List<String>> = sequenceOf(
        listOf("Dua 1", "Dua 2", "Dua 3")
    )
}

@Preview
@Composable
private fun DuaBottomSheetPreview(
    @PreviewParameter(DuaBottomSheetState::class) dua: List<String>
) {
    ImanBytesTheme {
        DuaBottomSheet(
            duaSheet = dua
        )
    }
}

class RamadanDayDetailS : PreviewParameterProvider<RamadanDayDetailState> {
    override val values: Sequence<RamadanDayDetailState>
        get() = sequenceOf(
            RamadanDayDetailState(
                day = RamadanCalender(
                    gregorianDate = "18 Feb 2026",
                    hijriDate = "21 Ramadan 1447",
                    holidays = listOf("Laylatul qadr", "Shab"),
                    imsak = "5:40 AM",
                    sunset = "6:40 PM",
                    prayerTimes = listOf(
                        "Imsak" to "05:04",
                        "Fajr" to "05:34",
                        "Sunrise" to "06:30",

                        )
                )
            )
        )
}

@Preview(showBackground = true)
@Composable
private fun RamadanDayDetailSScreenPreview(
    @PreviewParameter(RamadanDayDetailS::class) uiState: RamadanDayDetailState
) {
    ImanBytesTheme(dynamicColor = false) {
        RamadanDayDetailScreen(
            uiState = uiState
        )
    }
}
