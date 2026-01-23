package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.annotation.OptIn
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(UnstableApi::class)
@Composable
fun RecitationBottomSheet(
    modifier: Modifier = Modifier,
    showSheet: Boolean,
    onDismiss: (Boolean) -> Unit,
    onReadSurahClick: (String) -> Unit,
    player: Player? = null
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(showSheet) {
        if (showSheet) sheetState.expand() else sheetState.hide()
    }

    if (showSheet)
        ModalBottomSheet(
            onDismissRequest = { onDismiss(!showSheet) },
            sheetState = sheetState,
            dragHandle = null
        ) {
            RecitationBottomSheetContent(
                modifier = modifier,
                onReadSurahClick = onReadSurahClick,
                player = player
            )
        }
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationBottomSheet(
            showSheet = true,
            onDismiss = {},
            onReadSurahClick = {}
        )
    }
}