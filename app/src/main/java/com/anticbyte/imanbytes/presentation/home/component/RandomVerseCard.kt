package com.anticbyte.imanbytes.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.presentation.home.utils.shareVerse
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.shareCardText

@Composable
fun RandomVerseCard(
    modifier: Modifier = Modifier,
    verse: RandomVerse,
    onReadMore: (verseId: String) -> Unit = {}
) {
    val context = LocalContext.current
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HomeTitle(label = "Verse of the day")
        Card(
            modifier = Modifier,
            onClick = { onReadMore(verse.number.toString()) },
            shape = shapes.extraLarge,
            colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerLowest)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .graphicsLayer {
                            rotationZ = 180f
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_quote),
                    contentDescription = null,
                    tint = colorScheme.primaryContainer
                )
                Text(
                    verse.text,
                    maxLines = 3,
                    style = typography.headlineMedium,
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
                            context.shareCardText(text = verse.shareVerse())
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

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RandomVerseCard(
            verse = RandomVerse()
        )
    }
}