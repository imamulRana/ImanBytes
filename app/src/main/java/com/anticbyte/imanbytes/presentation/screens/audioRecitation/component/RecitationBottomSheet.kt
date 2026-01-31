package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.Player
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecitationBottomSheet(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onReadSurahClick: (String) -> Unit,
    player: Player? = null
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                // Determine if we need to clean up logic here
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onDismiss()
                    }
                }
            },
            sheetState = sheetState,
            dragHandle = null, // Custom drag handle or none
            modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars) // Handle edge-to-edge
        ) {
            RecitationBottomSheetContent(
                onReadSurahClick = { surahId ->
                    // Close sheet gracefully before navigation (optional preference)
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                            onReadSurahClick(surahId)
                        }
                    }
                },
                player = player,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationBottomSheet(
            isOpen = true,
            onDismiss = {},
            onReadSurahClick = {}
        )
    }
}