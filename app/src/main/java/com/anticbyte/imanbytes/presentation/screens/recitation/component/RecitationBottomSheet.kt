package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun RecitationBottomSheet(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlayBackActions,
    sheetState: SheetState,
    showSheet: Boolean,
    onSheetHide: (Boolean) -> Unit = {}
) {
    LaunchedEffect(showSheet) {
        if (showSheet) sheetState.expand() else sheetState.hide()
    }
    if (showSheet)
        ModalBottomSheet(onDismissRequest = { onSheetHide(false) }, sheetState = sheetState) {
            RecitationBottomSheetContent(
                modifier = modifier,
                playBackState = playBackState,
                actions = actions
            )
        }
}