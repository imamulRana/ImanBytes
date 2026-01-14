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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun RamadanDayDetailSheet(
    modifier: Modifier = Modifier,
    day: RamadanCalender,

    ) {
    ModalBottomSheet(
        {}, dragHandle = {},
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Dummy Text") },
                subtitle = {
                    Text(day.hijriDate)
                })
        }) { innerPadding ->
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
                                shapes = ListItemDefaults.segmentedShapes(index, day.holidays.size),
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
                        Row(horizontalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)) {
                            Card(
                                modifier = Modifier
                                    .weight(1f),
                                colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainer)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {

                                    Text("5:42", style = typography.displaySmall)
                                    Text("16:04")
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .weight(1f),
                                colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainer)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {

                                    Text("5:42", style = typography.displaySmall)
                                    Text("16:04")
                                }
                            }
                            /*SegmentedListItem(
                                modifier = Modifier.weight(1f),
                                shapes = ListItemDefaults.shapes(shapes.large),
                                onClick = {},
                                trailingContent = {
                                },
                                supportingContent = {
                                    Text("16:04")
                                },
                                colors = ListItemDefaults.segmentedColors(
                                    containerColor = colorScheme.surfaceContainer
                                )
                            ) {
                                Text("5:40", style = typography.displaySmall)
                            }
                            SegmentedListItem(
                                modifier = Modifier.weight(1f),
                                shapes = ListItemDefaults.shapes(shapes.large),
                                onClick = {},
                                supportingContent = {
                                    Text("16:04")
                                },
                                trailingContent = {
                                },
                                colors = ListItemDefaults.segmentedColors(
                                    containerColor = colorScheme.surfaceContainer
                                )
                            ) {
                                Text("5:42", style = typography.displaySmall)
                            }*/
                        }
                    }
                    itemsIndexed(day.prayerTimes) { index, prayerTime ->
                        SegmentedListItem(
                            shapes = ListItemDefaults.segmentedShapes(index, day.prayerTimes.size),
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
                }
            }
        }
    }
}

class RamadanDayDetailSheetStateProvider : PreviewParameterProvider<RamadanCalender> {
    override val values = sequenceOf(
        RamadanCalender(
            gregorianDate = "18 Feb 2026",
            hijriDate = "1 Ramadan 1447",
            prayerTimes = listOf(
                "Fajr" to "05:34",
                "Dhuhr" to "12:04",
                "Asr" to "15:34",
                "Maghrib" to "18:04",
            ),
            holidays = listOf("Eid-ul-Fitr"),
        ),
        RamadanCalender()
    )
}

@Preview(showSystemUi = true, device = PIXEL_9)
@Composable
private fun RamadanDayDetailSheetPreview(
    @PreviewParameter(RamadanDayDetailSheetStateProvider::class) day: RamadanCalender
) {
    ImanBytesTheme {
        RamadanDayDetailSheet(day = day)
    }
}