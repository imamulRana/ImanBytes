package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState


@Composable
fun RecitationBottomSheetContent(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlayBackActions
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        ) {
//            Text(state.nowPlayingSurah?.englishName.orEmpty(), style = typography.headlineLarge)
        }
        RecitationPlayBack(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            playBackState = playBackState,
            actions = actions
        )
    }
}