
package com.anticbyte.imanbytes.presentation.quran

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.domain.model.Quran
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun VerseScreen(
    modifier: Modifier = Modifier, verseData: Quran
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = verseData.verseTitle,
            modifier = Modifier.padding(bottom = 32.dp),
            style = typography.titleLarge
        )
        Text(
            text = verseData.verse,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.End
        )
        Text(
            text = verseData.verseTranslation,
            modifier = Modifier.padding(bottom = 32.dp),
            style = typography.bodyLarge,
            textAlign = TextAlign.Justify
        )
        Text(
            text = verseData.verseInfo,
            modifier = Modifier.align(Alignment.End),
            style = typography.bodyMedium,
            textAlign = TextAlign.Right
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FlashCardPreview() {
    ImanBytesTheme {
//        VerseScreen(verseData = quranData)
    }
}