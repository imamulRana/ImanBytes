package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun BoxScope.RecitationFloatingButton(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    listState: LazyListState,
    showScrollToTop: Boolean,
) {
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = showScrollToTop,
        modifier = modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .align(Alignment.BottomCenter)
            .offset(y = -(ScreenOffset.plus(78.dp))),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Button(onClick = {
            coroutineScope.launch {
                listState.animateScrollToItem(1)
            }
        }) {
            Text("Scroll to top")
        }
    }
}