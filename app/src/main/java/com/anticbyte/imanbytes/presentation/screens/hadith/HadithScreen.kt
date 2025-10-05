package com.anticbyte.imanbytes.presentation.screens.hadith

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.presentation.screens.textRecitation.TxtRecitationScreen

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
fun PillarScreen(
    modifier: Modifier = Modifier,
    pillarViewModel: PillarViewModel
) {
    Scaffold {
        val uiState by pillarViewModel.uiState.collectAsStateWithLifecycle()
        val originalVerses = uiState.pillarData.first
        val translatedVerses = uiState.pillarData.second
        val itemCount = minOf(originalVerses.size, translatedVerses.size)

        TxtRecitationScreen(uiState = uiState, contentPadding = it)
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