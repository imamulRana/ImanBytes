package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.anticbyte.imanbytes.domain.model.Surah

@Composable
fun RecitationBottomSheet(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlayBackActions,
    sheetState: SheetState,
    showSheet: Boolean,
    onSheetHide: (Boolean) -> Unit = {},
    nowPlayingSurah: Surah?,
    onReadSurahClick: (String) -> Unit
) {
    LaunchedEffect(showSheet) {
        if (showSheet) sheetState.expand() else sheetState.hide()
    }
    if (showSheet)
        ModalBottomSheet(onDismissRequest = { onSheetHide(false) }, sheetState = sheetState, dragHandle = null) {
            RecitationBottomSheetContent(
                modifier = modifier,
                playBackState = playBackState,
                actions = actions,
                nowPlayingSurah = nowPlayingSurah,
                onReadSurahClick = onReadSurahClick
            )
        }
}