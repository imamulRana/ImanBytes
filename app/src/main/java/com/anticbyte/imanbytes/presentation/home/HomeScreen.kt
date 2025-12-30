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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
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
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

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
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Iman Bytes",
                subtitle = "Your Islamic Companion"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding().plus(88.dp)
                )
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TitleWithContent(leadingContent = {
                VerticalDivider(
                    thickness = 4.dp,
                    modifier = Modifier.clip(shapes.medium)
                )
            }, title = "Verse of the day") {
                VerseOfTheDayCard()
            }
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

            TitleWithContent(title = "Asma al husna") {
                ElevatedCard() {
                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            color = colorScheme.surfaceVariant,
                            shape = CircleShape,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    state.asma.number.toString(),
                                    modifier = Modifier,
                                    style = typography.labelSmall
                                )
                            }
                        }
                        Text(
                            state.asma.name, style = typography.displayLarge.copy(
                                fontFamily = FontFamily(Font(R.font.lateef))
                            )
                        )
                        Text(state.asma.transliteration, style = typography.headlineSmall)
                        Text(state.asma.englishMeaning, style = typography.bodyLarge)
                        HorizontalDivider()
                        Text("99 Names", style = typography.labelMedium)
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
    ElevatedCard(
        modifier = modifier,
        onClick = {}) {
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
                    "2:255",
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
            HorizontalDivider(thickness = 0.5.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Sahih International", style = typography.bodySmall)
                Text("Sahih International", style = typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    ImanBytesTheme(darkTheme = false, dynamicColor = true) {
        HomeScreen()
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