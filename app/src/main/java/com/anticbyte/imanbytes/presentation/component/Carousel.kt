package com.anticbyte.imanbytes.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import kotlinx.coroutines.launch

@Composable
fun AppCarousel(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    images: List<Int>
) {
    val pagerState = rememberPagerState { 2 }
    Column(modifier.fillMaxSize()) {

        HorizontalPager(
            modifier = modifier,
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Draw from back to front for stacking
                for (i in images.lastIndex downTo 0) {
                    if (i >= pagerState.currentPage) {
                        val offset = (i - pagerState.currentPage) * 8
                        val scale = 1f - (i - pagerState.currentPage) * 0.05f
                        val alpha = 1f - (i - pagerState.currentPage) * 0.1f

                        Image(
                            painter = painterResource(id = images[i]),
                            contentDescription = "Stacked Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .graphicsLayer {
                                    translationY = offset * 10f
                                    scaleX = scale
                                    scaleY = scale
                                    this.alpha = alpha
                                }
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.navigateOnClick(): Modifier = composed {
    val scope = rememberCoroutineScope()
    pointerInput(Unit) {
        this.detectHorizontalDragGestures(
            onHorizontalDrag = { change, dragAmount ->
                change
            }
        )
    }
}

@Preview
@Composable
fun SwipableCardStack() {
    val images = listOf(R.drawable.img_quran_verse, R.drawable.img_hadith)
    var currentIndex by remember { mutableIntStateOf(0) }

    if (currentIndex >= images.size) {
        // All cards swiped
        Box(
            Modifier
                .navigateOnClick()
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No more images", fontSize = 20.sp)
        }
        return
    }

    val screenWidth = LocalWindowInfo.current.containerSize.width.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }

    val offsetX = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Draw stacked cards from back to front
        for (i in (images.lastIndex) downTo currentIndex) {
            val scale = 1f - (i - currentIndex) * 0.05f
            val translateY = (i - currentIndex) * 10f

            if (i == currentIndex) {
                // Top card with gesture
                Image(
                    painter = painterResource(id = images[i]),
                    contentDescription = "Card Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 4f)
                        .graphicsLayer {
                            translationX = offsetX.value
                            rotationZ = (offsetX.value / 60).coerceIn(-20f, 20f)
                            scaleX = scale
                            scaleY = scale
                            translationY = translateY
                        }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDrag = { _, dragAmount ->
                                    scope.launch {
                                        offsetX.snapTo(offsetX.value + dragAmount.x)
                                    }
                                },
                                onDragEnd = {
                                    when {
                                        offsetX.value > screenWidthPx / 4 -> {
                                            // Swiped right
                                            scope.launch {
                                                offsetX.animateTo(screenWidthPx) {
                                                    // spring effect
                                                    spring(
                                                        2f,
                                                        Spring.StiffnessMedium,
                                                        visibilityThreshold = null
                                                    )
                                                }
                                                offsetX.snapTo(0f)
                                                currentIndex++
                                            }
                                        }

                                        offsetX.value < -screenWidthPx / 4 -> {
                                            // Swiped left
                                            scope.launch {
                                                offsetX.animateTo(-screenWidthPx) {
                                                    spring(
                                                        2f,
                                                        Spring.StiffnessMedium,
                                                        visibilityThreshold = null
                                                    )
                                                }
                                                offsetX.snapTo(0f)
                                                currentIndex++
                                            }
                                        }

                                        else -> {
                                            // Snap back
                                            scope.launch {
                                                offsetX.animateTo(0f, tween(300))
                                            }
                                        }
                                    }
                                }
                            )
                        }
                )
            } else {
                // Stacked cards behind top card
                Image(
                    painter = painterResource(id = images[i]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 4f)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationY = translateY
                        }
                )
            }
        }
    }
}


data class AppCarouselItem(val title: String, val description: String)
class AppCarouselPreviewParameterProvider : PreviewParameterProvider<AppCarouselItem> {
    override val values: Sequence<AppCarouselItem>
        get() = sequenceOf(
            AppCarouselItem(
                "Verse of the day",
                "Regular quranic verse of the day to recharge your iman"
            ),
            AppCarouselItem("Hadith of the day", "Regular hadith of the day to recharge your iman"),
        )
}

@Preview
@Composable
private fun AppCarouselPreview(@PreviewParameter(provider = AppCarouselPreviewParameterProvider::class) item: AppCarouselItem) {
    ImanBytesTheme {
        AppCarousel(
            title = item.title, description = item.description, images = listOf(
                R.drawable.img_quran_verse, R.drawable.img_hadith
            )
        )
    }
}