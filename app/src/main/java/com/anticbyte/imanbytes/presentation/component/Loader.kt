package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun AppLoader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ContainedLoadingIndicator(
            modifier = Modifier,
            polygons = listOf(
                MaterialShapes.Pill,
                MaterialShapes.Oval,
                MaterialShapes.Clover4Leaf
            )
        )
    }
}

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun BoxScope.AppLoader(modifier: Modifier = Modifier) {
    ContainedLoadingIndicator(
        modifier = modifier.align(Alignment.Center),
        polygons = listOf(
            MaterialShapes.Pill,
            MaterialShapes.Oval,
            MaterialShapes.Clover4Leaf
        )
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Media3ProgressIndicator(
    modifier: Modifier = Modifier,
    player: ExoPlayer,
    updateInterval: Long = 100L
) {
    val currentProgress by produceState(0f, player.isPlaying) {
        while (true) {
            if (player.isPlaying) {
                val duration = player.duration.takeIf { it > 0 } ?: 1L
                val position = player.currentPosition
                value = (position / duration.toFloat()).coerceIn(0f, 1f)
            }
            delay(updateInterval)
        }
    }
    CircularWavyProgressIndicator(progress = { currentProgress })
}