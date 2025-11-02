package com.anticbyte.imanbytes.presentation.screens.hadith

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.domain.model.SurahText

@Composable
fun HadithScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Coming Soon!")
    }
}

@Composable
fun PillarList(
    modifier: Modifier = Modifier,
    surahText: SurahText,
    translationList: String
) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            "${surahText.text} ${surahText.numberInSurah}",
            modifier,
            style = typography.titleLarge,
            textAlign = TextAlign.Right
        )

        Text(translationList, modifier, style = typography.bodyMedium, textAlign = TextAlign.Left)
    }
}