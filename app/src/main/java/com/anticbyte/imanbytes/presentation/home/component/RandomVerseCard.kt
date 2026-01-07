package com.anticbyte.imanbytes.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.utils.shareCardText

@Composable
fun RandomVerseCard(modifier: Modifier = Modifier, verse: RandomVerse) {
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
                    Text(verse.surah.englishName, style = typography.titleLarge)
                    Text(verse.surah.englishNameTranslation, style = typography.bodySmall)
                }
                Text(
                    verse.numberInSurah.toString(),
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
                verse.text,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(verse.edition.englishName, style = typography.bodySmall)
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
