package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationListItem

@Composable
fun ArabicRecitation(
    modifier: Modifier = Modifier,
    state: RecitationState,
    isPlaying: Boolean,
    playAudio: (surahID: String) -> Unit,
    currentMediaId: String?
) {
    LazyColumn {
        translationRecitation(
            state = state,
            isPlaying = isPlaying,
            playAudio = playAudio,
            currentMediaId = currentMediaId
        )
    }
}

fun LazyListScope.translationRecitation(
    modifier: Modifier = Modifier,
    currentMediaId: String?,
    state: RecitationState,
    isPlaying: Boolean,

    playAudio: (surahID: String) -> Unit
) {
    items(state.surahList) { surah ->
        val isThisPlaying =
            (surah.number.plus(".${RecitationType.TRANSLATION.recitationId}") == currentMediaId) and isPlaying
        RecitationListItem(
            surah = surah,
            isPlaying = isThisPlaying,
            playAudio = playAudio
        )
        if (surah != state.surahList.last()) HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

    }
}