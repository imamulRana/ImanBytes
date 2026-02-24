package com.anticbyte.imanbytes.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalSlider
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Preview
@Composable
fun VerticalScrollScreen(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val sliderState = rememberSliderState()

    val scrollProgress by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.totalItemsCount == 0) return@derivedStateOf 0f

            val firstVisibleIndex = listState.firstVisibleItemIndex.toFloat()
            val firstVisibleOffset = listState.firstVisibleItemScrollOffset.toFloat()
            val itemSize = layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1

            val totalScrollableItems = layoutInfo.totalItemsCount - layoutInfo.visibleItemsInfo.size
            if (totalScrollableItems <= 0) return@derivedStateOf 0f

            val currentPosition = firstVisibleIndex + (firstVisibleOffset / itemSize)
            (currentPosition / totalScrollableItems).coerceIn(0f, 1f)
        }
    }

    // List → Slider sync
    LaunchedEffect(scrollProgress, sliderState.isDragging) {
        if (!sliderState.isDragging) {
            sliderState.value = scrollProgress
        }
    }

    // Slider → List sync
    LaunchedEffect(sliderState.value) {
        if (sliderState.isDragging) {
            val layoutInfo = listState.layoutInfo
            val totalScrollableItems = layoutInfo.totalItemsCount - layoutInfo.visibleItemsInfo.size
            if (totalScrollableItems <= 0) return@LaunchedEffect

            val targetPosition = sliderState.value * totalScrollableItems
            val targetIndex = targetPosition.toInt().coerceIn(0, layoutInfo.totalItemsCount - 1)
            val itemSize = layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
            val targetOffset = ((targetPosition - targetIndex) * itemSize).toInt()

            listState.scrollToItem(targetIndex, targetOffset)
        }
    }

    ImanBytesTheme {
        Scaffold {
            Box(Modifier.padding(it)) {
                LazyColumn(state = listState) {
                    items(100) {
                        ListItem(onClick = {}) {
                            Text("Item $it")
                        }
                    }
                }
                Button(onClick = {}, modifier = Modifier.align(Alignment.Center)) {
                    Text("Scroll to ${scrollProgress}")
                }
                VerticalSlider(
                    state = sliderState, modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}