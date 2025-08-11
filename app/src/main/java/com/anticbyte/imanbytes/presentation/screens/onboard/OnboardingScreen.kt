@file:OptIn(
    ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class
)

package com.anticbyte.imanbytes.presentation.screens.onboard

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anticbyte.imanbytes.presentation.component.AppOutlineButton
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit = {},
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Scaffold {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .zIndex(3f)
                    .aspectRatio(1 / 1f)
                    .offset(x = 150.dp, y = (-150).dp)
                    .rotate(rotationAngle) // Apply the infinite rotation
                    .align(alignment = Alignment.TopEnd)
                    .background(
                        color = colorScheme.primary,
                        MaterialShapes.Cookie6Sided.toShape()
                    )
            )

            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Welcome to ImanBytes,",
                    style = typography.displaySmall
                )
                Text(
                    text = "ImanBytes is here to connect you to the creator of you understand him",
                    style = typography.bodyLarge
                )
            }

            AppOutlineButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                onClick = { onContinue() },
                buttonLabel = "Continue"
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    ImanBytesTheme(darkTheme = true, dynamicColor = false) {
        OnboardingScreen()
    }
}