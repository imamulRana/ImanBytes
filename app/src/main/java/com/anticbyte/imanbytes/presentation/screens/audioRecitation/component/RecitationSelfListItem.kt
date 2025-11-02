package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun RecitationSelfListItem(
    modifier: Modifier = Modifier,
    surah: Surah,
    onItemClick: (String) -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .clickable(onClick = { onItemClick(surah.number) }),
        leadingContent = {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.secondaryContainer, CircleShape)
                    .size(size = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.number,
                    color = colorScheme.onSecondaryContainer,
                    style = typography.titleMedium
                )
            }
        },
        overlineContent = { Text(surah.name) },
        headlineContent = { Text(text = surah.englishName) },
        supportingContent = { Text(text = surah.englishNameTranslation) },
        trailingContent = {
            Text(text = "${surah.numberOfAyahs} Ayahs")
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationSelfListItem(
            surah = Surah(
                number = "1",
                name = "Al-Fatiha",
                englishName = "Al-Fatiha",
                englishNameTranslation = "The Opening",
                revelationType = "Meccan",
                numberOfAyahs = "7"
            )
        )
    }
}