package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.presentation.component.AppTopBar

@Composable
fun RecitationSelfBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: RecitationSelfDetailUiState,
    showSheet: Boolean
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    if (showSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = null
        ) {
            AppTopBar(
                title = "Tafsir",
                isBackVisible = true,
                scrollBehavior = scrollBehavior,
                onNavigationIconClick = onDismiss,
                subtitle = state.tafsir.tafsirs.lastOrNull()?.author.orEmpty(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
            LazyColumn(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                state.tafsir.tafsirs.lastOrNull()?.groupVerse?.let { group ->
                    item {
                        ListItem(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            onClick = {}, shapes = ListItemDefaults.shapes(
                                shape = shapes.medium
                            )
                        ) {
                            Text(text = group)
                        }
                    }
                }
                item {
                    Text(
                        text = state.tafsir.tafsirs.lastOrNull()?.content.orEmpty(),
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}