package com.anticbyte.imanbytes.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.PrayerTime
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.home.utils.shareAsma
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.shareCardText

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    onCLick: () -> Unit,
    isActionAvailable: Boolean = false,
    headerText: String,
    overLineText: String? = null
) {
    ListItem(modifier = modifier, onClick = onCLick, overlineContent = {
        overLineText?.let {
            Text(text = it, style = typography.bodySmall, color = colorScheme.onSurfaceVariant)
        }
    }, trailingContent = {
        if (isActionAvailable) Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
            null,
            tint = colorScheme.onSurfaceVariant
        )
    }) {
        Text(
            text = headerText, style = typography.labelLarge, color = colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PrayerTimeSection(
    modifier: Modifier = Modifier, prayerTime: PrayerTime
) {

    Column(modifier = modifier) {
        SectionHeader(
            headerText = "Prayer times", onCLick = {}, overLineText = prayerTime.readableDate
        )
        Column(
            modifier = Modifier.padding(
                horizontal = 8.dp, vertical = 4.dp
            ), verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            prayerTime.prayerTime.fastForEachIndexed { index, (prayer, time) ->
                SegmentedListItem(
                    onClick = {}, leadingContent = {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                PrayerTimeSec.entries[index].icon
                            ), null, tint = colorScheme.onPrimaryContainer
                        )
                    }, shapes = ListItemDefaults.segmentedShapes(
                        index, prayerTime.prayerTime.size
                    ), trailingContent = {
                        Text(text = time)
                    }, colors = ListItemDefaults.segmentedColors(colorScheme.surfaceContainerLowest)
                ) {
                    Text(prayer, style = typography.labelLarge)
                }
            }
        }
    }
}

fun Modifier.negativePadding(): Modifier = composed {
    layout { measurable, constraints ->
        val offset = constraints.offset(horizontal = 32.dp.roundToPx())
        val placeable = measurable.measure(offset)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}


@Composable
fun RandomVerseSection(
    modifier: Modifier = Modifier, verse: RandomVerse, onReadMore: (verseId: String) -> Unit = {}
) {
    val shareText = """
    ﴾ ${verse.text} ﴿

— Surah ${verse.surah.englishName}
Ayah ${verse.numberInSurah}

May this verse be a reminder for the heart 🤍
""".trimIndent()
    val context = LocalContext.current
    Column(modifier = modifier) {
        SectionHeader(
            onCLick = { onReadMore(verse.number.toString()) },
            headerText = "Verse of the day",
            isActionAvailable = true
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                verse.text,
                maxLines = 4,
                style = typography.headlineSmall,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HorizontalDivider(
                    modifier = Modifier.width(16.dp), color = colorScheme.outline
                )
                Text(
                    "${verse.surah.englishName} ${verse.surah.number}: ${verse.numberInSurah}",
                    style = typography.bodySmall,
                    color = colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.weight(1f))
                FilledTonalIconButton(
                    modifier = Modifier.size(
                        IconButtonDefaults.smallContainerSize(
                            widthOption = IconButtonDefaults.IconButtonWidthOption.Wide
                        )
                    ), onClick = {
                        context.shareCardText(text = shareText)
                    }, shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill), null,
                        modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                    )
                }
            }
        }
    }
}

@Composable
fun AsmaSection(
    asma: Asma, onNavigateToAsma: () -> Unit = {}
) {
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HomeTitle(label = "Asma al husna", action = onNavigateToAsma)
        Card(
            shape = shapes.extraLarge,
            colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLowest)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            ) {
                AppAvatar {
                    Text(
                        asma.number.toString().padStart(2, '0').take(2),
                        style = typography.titleMedium,
                        color = colorScheme.onPrimaryContainer
                    )
                }
                Text(
                    modifier = Modifier, text = asma.name, style = typography.displaySmall.copy(
                        fontFamily = FontFamily(
                            Font(R.font.scheherazade),
                        )
                    )
                )
                Text(asma.transliteration, style = typography.headlineSmall)
                Text(asma.englishMeaning, style = typography.titleSmall)
                Spacer(Modifier.size(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${asma.number}/ 99 names of Allah", style = typography.bodySmall)
                    FilledTonalIconButton(
                        modifier = Modifier.size(
                            IconButtonDefaults.smallContainerSize(
                                widthOption = IconButtonDefaults.IconButtonWidthOption.Wide
                            )
                        ), onClick = {
                            context.shareCardText(text = asma.shareAsma())
                        }, shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill),
                            null,
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RamadanSection(
    modifier: Modifier = Modifier, prayerTime: PrayerTime
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HomeTitle(label = prayerTime.hijriDate)
        FlowRow(
            maxItemsInEachRow = 2, modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RamadanOverViewCard(
                modifier = Modifier.weight(1f),
                prayerTime = prayerTime.suhoor,
                timeLabel = "Suhoor",
                icon = R.drawable.sunny_24px,
                iconTint = Color(0xFFF3D769)
            )
            RamadanOverViewCard(
                modifier = Modifier.weight(1f),
                prayerTime = prayerTime.iftaar,
                timeLabel = "Iftaar",
                icon = R.drawable.bedtime_24px,
                iconTint = Color(0xFF868CFA)
            )
        }
    }
}

@Composable
fun RamadanOverViewCard(
    modifier: Modifier = Modifier,
    timeLabel: String,
    prayerTime: String,
    @DrawableRes icon: Int = R.drawable.sunny_24px,
    iconTint: Color = Color.Unspecified
) {
    Card(
        modifier = modifier,
        shape = shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLowest)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = null,
                    tint = iconTint
                )
                Text(timeLabel, style = typography.bodySmall)
            }
            Text(
                prayerTime.substringBefore(" "),
                style = typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                prayerTime.substringAfter(" "),
                style = typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun RamadanSectionPrev(
    @PreviewParameter(RamadanSectionPreviewProvider::class) prayerTime: PrayerTime
) {
    ImanBytesTheme {
        RamadanSection(prayerTime = prayerTime)
    }
}

class RamadanSectionPreviewProvider : PreviewParameterProvider<PrayerTime> {
    override val values: Sequence<PrayerTime>
        get() = sequenceOf(
            PrayerTime(
                prayerTime = listOf(
                    "Fajr" to "04:30",
                    "Dhuhr" to "12:30",
                    "Asr" to "16:30",
                    "Maghrib" to "18:30",
                    "Isha" to "19:30"
                ),
                suhoor = "04:30",
                iftaar = "04:30",
                hijriDate = "01-07-1447",
                gregorianDate = "18-02-2026",
                readableDate = "February 18, 2026"
            )
        )
}


class RandomVersePreviewProvider : PreviewParameterProvider<RandomVerse> {
    override val values: Sequence<RandomVerse>
        get() = sequenceOf(
            RandomVerse(
                surah = Surah(
                    number = "1",
                    name = "Al Fatiha",
                    englishName = "Al Fatiha",
                ),
                text = "Allah - there is no deity except Him, the Ever-Living, the Sustainer of [all] existence. Neither drowsiness overtakes Him nor sleep. To Him belongs whatever is in the heavens and whatever is on the earth. Who is it that can intercede with Him except by His permission? He knows what is [presently] before them and what will be after them, and they encompass not a thing of His knowledge except for what He wills. His Kursi extends over the heavens and the earth, and their preservation tires Him not. And He is the Most High, the Most Great."
            )
        )
}

@Preview(showBackground = true)
@Composable
private fun RandomVerseSectionPrev(
    @PreviewParameter(RandomVersePreviewProvider::class) verse: RandomVerse
) {
    ImanBytesTheme {
        RandomVerseSection(verse = verse)
    }
}

@Preview
@Composable
private fun HomeTitlePrev() {
    ImanBytesTheme {
        SectionHeader(onCLick = {}, headerText = "Prayer")
    }
}