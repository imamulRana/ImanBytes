package com.anticbyte.imanbytes.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────
// State
// ─────────────────────────────────────────────

/**
 * Holds the current position [0f..1f] and drag state of the scroll bar thumb.
 *
 * Obtain an instance with [rememberSliderState].
 */
@Stable
class SliderState(initialValue: Float = 0f) {
    var value by mutableFloatStateOf(initialValue.coerceIn(0f, 1f))
        internal set

    var isDragging by mutableStateOf(false)
        internal set

    /** Programmatically move the thumb. Ignored while the user is dragging. */
    fun snapTo(progress: Float) {
        if (!isDragging) value = progress.coerceIn(0f, 1f)
    }
}

@Composable
fun rememberSliderState(initialValue: Float = 0f): SliderState =
    remember { SliderState(initialValue) }

// ─────────────────────────────────────────────
// Scroll ↔ Slider sync logic (reusable hook)
// ─────────────────────────────────────────────

/**
 * Connects a [LazyListState] to a [SliderState] so that:
 * - scrolling the list moves the thumb, and
 * - dragging the thumb scrolls the list.
 *
 * Call this inside any composable that owns both states.
 */
@Composable
fun SyncScrollWithSlider(
    listState: LazyListState,
    sliderState: SliderState,
) {
    // Derive scroll progress from the list's layout info
    val scrollProgress by remember(listState) {
        derivedStateOf {
            val layout = listState.layoutInfo
            val totalItems = layout.totalItemsCount
            if (totalItems == 0) return@derivedStateOf 0f

            // How many items fit in the viewport (as a fraction)
            val visibleItemsFraction = layout.visibleItemsInfo.size.toFloat() / totalItems
            if (visibleItemsFraction >= 1f) return@derivedStateOf 0f

            val firstIndex = listState.firstVisibleItemIndex.toFloat()
            val firstOffset = listState.firstVisibleItemScrollOffset.toFloat()
            val itemSize = layout.visibleItemsInfo.firstOrNull()?.size?.takeIf { it > 0 } ?: 1

            val currentPosition = firstIndex + (firstOffset / itemSize)
            // Normalize against the truly scrollable range
            val scrollableRange = totalItems * (1f - visibleItemsFraction)

            (currentPosition / scrollableRange).coerceIn(0f, 1f)
        }
    }
    // List → Slider: update thumb when the list scrolls (but not while dragging)
    LaunchedEffect(scrollProgress, sliderState.isDragging) {
        if (!sliderState.isDragging) {
            sliderState.value = scrollProgress
        }
    }

    // Slider → List: scroll the list when the thumb is dragged
    LaunchedEffect(sliderState.value) {
        if (!sliderState.isDragging) return@LaunchedEffect

        val layout = listState.layoutInfo
        val totalItems = layout.totalItemsCount
        val visibleItemsFraction = layout.visibleItemsInfo.size.toFloat() / totalItems
        val scrollableRange = totalItems * (1f - visibleItemsFraction)

        val targetPosition = sliderState.value * scrollableRange
        val targetIndex = targetPosition.toInt().coerceIn(0, totalItems - 1)
        val itemSize = layout.visibleItemsInfo.firstOrNull()?.size ?: 0
        val targetOffset = ((targetPosition - targetIndex) * itemSize).toInt()

        listState.scrollToItem(targetIndex, targetOffset)
    }
}

// ─────────────────────────────────────────────
// Public composable
// ─────────────────────────────────────────────

/**
 * A vertical scroll bar that stays in sync with a [LazyListState].
 *
 * ### Minimal usage
 * ```kotlin
 * val listState  = rememberLazyListState()
 * val sliderState = rememberSliderState()
 *
 * Row {
 *     LazyColumn(state = listState, modifier = Modifier.weight(1f)) { … }
 *     VerticalScrollBar(listState = listState, sliderState = sliderState)
 * }
 * ```
 *
 * @param modifier      Applied to the [VerticalSlider] track.
 * @param sliderState   Thumb position & drag state; share this if you need
 *                      external control (e.g. keyboard shortcuts).
 * @param listState     The [LazyListState] of the list this bar controls.
 */
@Composable
fun VerticalScrollBar(
    modifier: Modifier = Modifier,
    sliderState: SliderState = rememberSliderState(),
    listState: LazyListState = rememberLazyListState(),
) {
    // Wire up the two-way sync
    SyncScrollWithSlider(listState = listState, sliderState = sliderState)

    // Render the thumb track
    VerticalSlider(
        state = sliderState,
        modifier = modifier,
    )
}

// ─────────────────────────────────────────────
// VerticalSlider (visual + drag input)
// ─────────────────────────────────────────────
//
// Replace with your own implementation if you already have one.
// This stub wires up vertical drag → SliderState so the contract is clear.

/**
 * A bare-bones vertical slider track.
 *
 * Swap this out for your own branded implementation; the only contract is:
 * - set [SliderState.isDragging] = true on drag start, false on drag end
 * - update [SliderState.value] continuously during a drag
 */
@Composable
internal fun VerticalSlider(
    state: SliderState,
    modifier: Modifier = Modifier,
) {
    val thumbHeight = 48.dp
    val thumbWidth = 4.dp
    val touchTargetWidth = 32.dp  // wider touch area
    val horizontalPadding = 4.dp // pushes thumb away from edge

    var trackHeightPx by remember { mutableFloatStateOf(1f) }
    val thumbHeightPx = with(LocalDensity.current) { thumbHeight.toPx() }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(touchTargetWidth)          // ✅ wide touch target
            .padding(horizontal = horizontalPadding) // ✅ thumb visually inset from edge
            .onSizeChanged { trackHeightPx = it.height.toFloat() }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    val travelRange = trackHeightPx - thumbHeightPx
                    if (travelRange > 0f) {
                        state.value = (state.value + delta / travelRange).coerceIn(0f, 1f)
                    }
                },
                onDragStarted = { state.isDragging = true },
                onDragStopped = { state.isDragging = false },
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd) // ✅ thumb hugs the right side of the touch area
                .offset(y = with(LocalDensity.current) {
                    ((trackHeightPx - thumbHeightPx) * state.value).toDp()
                })
                .width(thumbWidth)
                .height(thumbHeight)
                .clip(RoundedCornerShape(2.dp))
                .background(
                    if (state.isDragging)
                        MaterialTheme.colorScheme.primary else
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )
    }
}