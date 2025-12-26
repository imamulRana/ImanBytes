package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.annotation.OptIn
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.domain.model.Surah

@OptIn(UnstableApi::class)
@Composable
fun RecitationBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    showSheet: Boolean,
    onSheetHide: (Boolean) -> Unit = {},
    nowPlayingSurah: Surah?,
    onReadSurahClick: (String) -> Unit,
    player: Player
) {
    LaunchedEffect(showSheet) {
        if (showSheet) sheetState.expand() else sheetState.hide()
    }
    if (showSheet)
        ModalBottomSheet(
            onDismissRequest = { onSheetHide(false) },
            sheetState = sheetState,
            dragHandle = null
        ) {
            RecitationBottomSheetContent(
                modifier = modifier,
                nowPlayingSurah = nowPlayingSurah,
                onReadSurahClick = onReadSurahClick,
                player = player
            )
        }
}