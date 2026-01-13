package com.anticbyte.imanbytes.presentation.ramadan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.presentation.component.AppTopBar

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
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Ramadan",
                subtitle = "Ramadan calender 2026",
                isBackVisible = true,
                onNavigationIconClick = onNavigateUp
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn() {
                itemsIndexed(state.monthPrayerTime) { index, ramadan ->
                    SegmentedListItem(
                        onClick = {},
                        shapes = ListItemDefaults.segmentedShapes(index, state.monthPrayerTime.size)
                    ) {
                        Text(ramadan.hijriDate)
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
                    hijriDate = "1 Ramadan 1447"
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
    RamadanCalendarScreen(
        state = state
    )
}