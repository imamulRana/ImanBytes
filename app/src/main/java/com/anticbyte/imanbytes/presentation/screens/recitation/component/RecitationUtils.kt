package com.anticbyte.imanbytes.presentation.screens.recitation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.util.concurrent.TimeUnit

fun Modifier.paddingWithoutTop(paddingValues: Dp): Modifier = composed {
    padding(
        start = paddingValues, end = paddingValues, bottom = paddingValues
    )
}

fun PaddingValues.customInnerPadding() = PaddingValues(
    top = this.calculateTopPadding(),
    start = this.calculateStartPadding(LayoutDirection.Ltr),
    end = this.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding().plus(88.dp)
)

@SuppressLint("DefaultLocale")
fun Long.toTimeLine(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    return if (hours > 0)
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    else String.format("%02d:%02d", minutes, seconds)
}
