package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun Modifier.paddingWithoutTop(paddingValues: Dp): Modifier = composed {

    padding(
        start = paddingValues, end = paddingValues, bottom = paddingValues
    )
}

fun PaddingValues.customInnerPadding() = PaddingValues(
    top = this.calculateTopPadding(),
    start = this.calculateStartPadding(LayoutDirection.Ltr),
    end = this.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding().plus(48.dp)
)
