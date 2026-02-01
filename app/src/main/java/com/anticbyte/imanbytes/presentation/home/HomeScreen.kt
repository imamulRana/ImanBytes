package com.anticbyte.imanbytes.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.TitleAndContentSection
import com.anticbyte.imanbytes.presentation.home.component.AsmaAlHusnaCard
import com.anticbyte.imanbytes.presentation.home.component.ComposableWithTitle
import com.anticbyte.imanbytes.presentation.home.component.PrayerTimeCard
import com.anticbyte.imanbytes.presentation.home.component.PrayerTimeSec
import com.anticbyte.imanbytes.presentation.home.component.RandomVerseCard
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.lzColCustomPadding
import com.anticbyte.imanbytes.utils.to12Hour

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToRandomVerse: (verseId: String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        navigateToRandomVerse = navigateToRandomVerse,
        onRefresh = viewModel::refresh
    )
}

@Composable
fun HomeScreen(
    state: HomeScreenState = HomeScreenState(),
    navigateToRandomVerse: (verseId: String) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = topBarState)
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Iman Bytes",
                subtitle = "Your Islamic Companion",
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
            if (state.isLoading) AppLoader()
            else if (state.error != null) AppErrorScreen(
                errorMessage = state.error,
                onRetry = {})
            else
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing, onRefresh = onRefresh,
                ) {
                    LazyColumn(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = lzColCustomPadding,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            ComposableWithTitle(title = "prayer time") {
                                state.prayerTimes.fastForEachIndexed { index, (prayer, time) ->
                                    SegmentedListItem(
                                        onClick = {},
                                        leadingContent = {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(
                                                    PrayerTimeSec.entries[index].icon
                                                ), null,
                                                tint = colorScheme.onPrimaryContainer
                                            )
                                        },
                                        shapes = ListItemDefaults.segmentedShapes(
                                            index,
                                            state.prayerTimes.size
                                        ),
                                        trailingContent = {
                                            Text(
                                                time.to12Hour()
                                            )
                                        },
                                        colors = ListItemDefaults.segmentedColors(colorScheme.surfaceContainerLowest)
                                    ) {
                                        Text(prayer, style = typography.labelLarge)
                                    }
                                }
                            }
                        }
                        item {
                            TitleAndContentSection(title = "Verse of the day") {
                                RandomVerseCard(
                                    verse = state.randomVerse,
                                    onReadMore = navigateToRandomVerse
                                )
                            }
                        }
                        item {
                            TitleAndContentSection(title = "Asma al husna") {
                                AsmaAlHusnaCard(asma = state.asma)
                            }
                        }
                    }
                }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPrev() {
    ImanBytesTheme(darkTheme = false, dynamicColor = false) {
        HomeScreen(
            state = HomeScreenState(
                isLoading = false,
                prayerTimes = listOf(
                    "Fajr" to "04:30",
                    "Dhuhr" to "12:30",
                    "Asr" to "16:30",
                    "Maghrib" to "18:30",
                    "Isha" to "19:30"
                )
            )
        )
    }
}

@Preview
@Composable
private fun AsmaAlHusnaPreview() {
    ImanBytesTheme {
        AsmaAlHusnaCard(
            asma = Asma(
                name = "الرَّحْمَنُ",
                transliteration = "Al-Fatiha",
                englishMeaning = " The Opening",
                number = 1

            )
        )
    }

}

@Preview
@Composable
private fun PrayerTimeItemPreview() {
    ImanBytesTheme {
        PrayerTimeCard(
            prayerName = "Fajr",
            prayerTime = "04:30",
        )
    }
}

@Composable
fun RamadanCard(modifier: Modifier = Modifier) {
    ElevatedCard(onClick = {}) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            ListItem(
                headlineContent = {
                    Text("Ramadan")
                },
                supportingContent = {
                    Text("1447")
                },
                trailingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
                        null
                    )
                },
                colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainerLow)
            )
            /*Column {
                Row() {
                    Text("1 Ramadan", style = typography.titleLarge)
                    Text("1447")
                }
                Text("18 Feb 2026")
            }*/
            Spacer(modifier = Modifier.size(16.dp))
            SegmentedListItem(
                onClick = {},
                shapes = ListItemDefaults.segmentedShapes(0, 2),
                overlineContent = {
                    Text("Suhoor ends")
                }
            ) {
                Text("5:30")
            }
            SegmentedListItem(
                onClick = {},
                shapes = ListItemDefaults.segmentedShapes(1, 2),
                overlineContent = {
                    Text("Iftaar begins")
                }
            ) {
                Text("5:30")
            }
        }
    }
}

@Preview
@Composable
private fun RamadanCardPreview() {
    ImanBytesTheme {
        RamadanCard()
    }
}

@Preview
@Composable
private fun CurrentVersePreview() {
    ImanBytesTheme {
        RandomVerseCard(verse = RandomVerse())
    }
}