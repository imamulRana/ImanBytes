package com.anticbyte.imanbytes.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.shareCardText

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(modifier = modifier, state = state)
}

@Composable
fun TitleWithContent(
    modifier: Modifier = Modifier,
    title: String = "",
    leadingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingContent != null)
                leadingContent()
            Text(title.uppercase(), style = typography.labelSmall)
        }
        content()
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState = HomeScreenState()
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
                    isRefreshing = true, onRefresh = {},
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 24.dp,
                            bottom = 88.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            TitleWithContent(leadingContent = {
                                VerticalDivider(
                                    thickness = 4.dp,
                                    modifier = Modifier.clip(shapes.medium)
                                )
                            }, title = "Verse of the day") {
                                VerseOfTheDayCard()
                            }
                        }
                        item {
                            TitleWithContent(

                                leadingContent = {}, title = "Prayer Times"
                            ) {
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
                            TitleWithContent(title = "Asma al husna") {
                                AsmaAlHusnaCard(asma = state.asma)
                            }
                        }
                    }
                }
        }
    }
}

@Composable
fun PrayerTimeCard(
    modifier: Modifier = Modifier,
    prayerName: String = "",
    prayerTime: String,
    @DrawableRes prayerIconRes: Int = R.drawable.ic_asr
) {
    ElevatedCard(
        onClick = {}
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(color = colorScheme.surfaceVariant, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(prayerIconRes),
                    null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(prayerName, style = typography.bodySmall)
                Text(prayerTime, style = typography.labelSmall)
            }
        }
    }
}

@Composable
fun VerseOfTheDayCard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = modifier,
        onClick = {},
        shape = shapes.extraLarge
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Al-Baqara", style = typography.titleLarge)
                    Text("The Cow", style = typography.bodySmall)
                }
                Text(
                    "255",
                    style = typography.headlineSmall,
                    color = LocalContentColor.current.copy(.5f)
                )
            }
            val gradient = Brush.verticalGradient(
                listOf(
                    Color.Transparent,
                    colorScheme.surfaceContainerLow
                )
            )
            val textMeasurer = rememberTextMeasurer()
            val style = typography.bodyLarge
            val text = "Tap to read more"

            val textLayoutResult = textMeasurer.measure(
                text = text,
                style = style.copy(color = Color.White)
            )
            Text(
                stringResource(R.string.test_verse_translation),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Sahih International", style = typography.bodySmall)
                FilledTonalIconButton(
                    onClick = {
                        context.shareCardText(
                            "Verse of the day"
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(IconButtonDefaults.extraSmallContainerSize()),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(IconButtonDefaults.extraSmallIconSize),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill),
                        contentDescription = null
                    )
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

@Composable
fun AsmaAlHusnaCard(
    modifier: Modifier = Modifier,
    asma: Asma
) {
    val context = LocalContext.current
    ElevatedCard(shape = shapes.extraLarge) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorScheme.primaryContainer,
                            shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(asma.number.toString(), style = typography.titleMedium)
                }
                Text(
                    asma.name, style = typography.displayLarge.copy(
                        fontFamily = FontFamily(Font(R.font.lateef))
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    asma.transliteration,
                    style = typography.titleLarge
                )
                Text(asma.englishMeaning, style = typography.bodyMedium)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("${asma.number} / 99 Names of Allah", style = typography.bodySmall)
                FilledTonalIconButton(
                    onClick = {
                        context.shareCardText("$asma")
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(IconButtonDefaults.extraSmallContainerSize()),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(IconButtonDefaults.extraSmallIconSize),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill),
                        contentDescription = null
                    )
                }
            }
        }
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
        VerseOfTheDayCard()
    }
}