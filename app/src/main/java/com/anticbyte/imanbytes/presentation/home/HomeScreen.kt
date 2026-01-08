package com.anticbyte.imanbytes.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
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
import com.anticbyte.imanbytes.presentation.home.component.PrayerTimeCard
import com.anticbyte.imanbytes.presentation.home.component.RandomVerseCard
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToRandomVerse: (verseId: String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = modifier, state = state,
        navigateToRandomVerse = navigateToRandomVerse
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState = HomeScreenState(),
    navigateToRandomVerse: (verseId: String) -> Unit = {},
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state = topBarState)
    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            AppTopBar(
                title = "Iman Bytes",
                subtitle = "Your Islamic Companion",
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (state.isLoading) AppLoader()
            else if (state.error != null) AppErrorScreen(
                errorMessage = state.error,
                onRetry = {})
            else
                PullToRefreshBox(
                    isRefreshing = false, onRefresh = {},
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 24.dp,
                            bottom = 88.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            TitleAndContentSection(leadingContent = {
                                VerticalDivider(
                                    thickness = 4.dp,
                                    modifier = Modifier.clip(shapes.medium)
                                )
                            }, title = "Verse of the day") {
                                RandomVerseCard(
                                    verse = state.randomVerse,
                                    onReadMore = navigateToRandomVerse
                                )
                            }
                        }
                        item {
                            TitleAndContentSection(title = "Prayer Times") {
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .layout { measurable, constrains ->
                                            val newCon = constrains.offset(32.dp.roundToPx())
                                            val placeable = measurable.measure(newCon)
                                            layout(placeable.width, placeable.height) {
                                                placeable.placeRelative(0, 0)
                                            }
                                        },
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    item {
                                        PrayerTimeCard(
                                            prayerTime = state.prayerTimes.fajr,
                                            prayerName = "Fajr",
                                            prayerIconRes = R.drawable.ic_fajr
                                        )
                                    }
                                    item {
                                        PrayerTimeCard(
                                            prayerTime = state.prayerTimes.dhuhr,
                                            prayerName = "Dhuhr",
                                            prayerIconRes = R.drawable.ic_duhr
                                        )
                                    }
                                    item {
                                        PrayerTimeCard(
                                            prayerTime = state.prayerTimes.asr,
                                            prayerName = "Asr",
                                            prayerIconRes = R.drawable.ic_asr
                                        )
                                    }
                                    item {
                                        PrayerTimeCard(
                                            prayerTime = state.prayerTimes.maghrib,
                                            prayerName = "Maghrib",
                                            prayerIconRes = R.drawable.ic_magrib
                                        )
                                    }
                                    item {
                                        PrayerTimeCard(
                                            prayerTime = state.prayerTimes.isha,
                                            prayerName = "Isha",
                                            prayerIconRes = R.drawable.ic_isha
                                        )
                                    }
                                }
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


@Preview
@Composable
private fun HomeScreenPrev() {
    ImanBytesTheme(darkTheme = false, dynamicColor = true) {
        HomeScreen(
            state = HomeScreenState(
                isLoading = false,
                error = "a network occoured"
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

@Preview
@Composable
private fun CurrentVersePreview() {
    ImanBytesTheme {
        RandomVerseCard(verse = RandomVerse())
    }
}