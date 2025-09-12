package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationState
@Composable
fun RecitationBottomSheetContent(
    modifier: Modifier = Modifier,
    state: RecitationState,
    recitationTimeline: Pair<Long, Long>,
    isPlaying: Boolean = false,
    onPlayPause: () -> Unit = {},
    onSeekForward: () -> Unit = {},
    onSeekBackward: () -> Unit = {},
    progress: () -> Float = { 0f }
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(state.nowPlayingSurah?.englishName.orEmpty(), style = typography.headlineLarge)
        }
        RecitationPlayBack(
            modifier = Modifier.align(Alignment.BottomCenter),
            audioTimeLine = recitationTimeline,
            progress = progress,
            isPlaying = isPlaying,
            onPlayPause = onPlayPause,
            onSeekForward = onSeekForward,
            onSeekBackward = onSeekBackward,
        )
    }
}