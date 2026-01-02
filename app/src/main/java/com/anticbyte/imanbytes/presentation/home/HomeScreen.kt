package com.anticbyte.imanbytes.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

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
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Iman Bytes",
                subtitle = "Your Islamic Companion"
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
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
            TitleWithContent(leadingContent = {}, title = "Prayer Times") {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        PrayerTimeCard(
                            prayerTime = "04:30 AM",
                            prayerName = "Fajr",
                            prayerIconRes = R.drawable.ic_fajr
                        )
                    }
                    item {
                        PrayerTimeCard(
                            prayerTime = "1:00 PM",
                            prayerName = "Dhuhr",
                            prayerIconRes = R.drawable.ic_duhr
                        )
                    }
                    item {
                        PrayerTimeCard(
                            prayerTime = "5:00 PM",
                            prayerName = "Asr",
                            prayerIconRes = R.drawable.ic_asr
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrayerTimeCard(
    modifier: Modifier = Modifier,
    prayerTime: String = "",
    prayerName: String = "",
    @DrawableRes prayerIconRes: Int = R.drawable.ic_asr
) {
    ElevatedCard() {
        Column(
            modifier = modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 24.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(color = colorScheme.surfaceVariant, shape = shapes.medium),
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
                Text(prayerName)
                Text(prayerTime, style = typography.bodySmall)
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
                Column {
                    Text("Al-Baqara", style = typography.headlineSmallEmphasized)
                    Text("The Cow", style = typography.bodyLarge)
                }
                Text(
                    "255",
                    style = typography.headlineLarge,
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
            HorizontalDivider(thickness = 0.1.dp)
            Row() {
                Text("Surah Al-Fatihah (1:1)", style = typography.bodySmall)
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
        PrayerTimeCard()
    }
}

@Preview
@Composable
private fun CurrentVersePreview() {
    ImanBytesTheme {
        VerseOfTheDayCard()
    }
}