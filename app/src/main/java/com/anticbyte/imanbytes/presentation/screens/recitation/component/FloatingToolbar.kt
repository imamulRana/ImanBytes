package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationFloatingToolbar(modifier: Modifier = Modifier) {
    HorizontalFloatingToolbar(
        expanded = true,
        floatingActionButton = {
            FloatingToolbarDefaults.StandardFloatingActionButton(
                onClick = {},
                shape = FloatingActionButtonDefaults.largeShape
            ) { }
        },
        modifier = modifier
            .offset(
                y = -(WindowInsets.navigationBars.asPaddingValues()
                    .calculateBottomPadding()).plus(ScreenOffset)
            ),
        shape = shapes.medium
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Column {
                Text("Headline")
                Text("Supporting Text", style = typography.labelSmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationFloatingBar(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    onExpand: () -> Unit = {},
    onClick: () -> Unit = {},
    state: RecitationState
) {
    HorizontalFloatingToolbar(
        expanded = true, modifier = modifier
            .clip(shape = shapes.extraExtraLarge)
            .clickable(onClick = onExpand)
            .height(
                IntrinsicSize.Min
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(color = colorScheme.surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.nowPlayingSurah?.number.orEmpty(),
                    color = colorScheme.onSurface,
                    style = typography.titleMedium
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = state.nowPlayingSurah?.englishName.orEmpty(),
                    style = typography.bodyLarge
                )
                Text(
                    text = state.nowPlayingSurah?.englishNameTranslation.orEmpty(),
                    style = typography.bodyMedium
                )
            }
        }
        FilledTonalIconButton(
            onClick = onClick,
            modifier = Modifier
                .size(IconButtonDefaults.mediumContainerSize(widthOption = IconButtonDefaults.IconButtonWidthOption.Wide)),
            shapes = IconButtonDefaults.shapes(
                shape = IconButtonDefaults.mediumRoundShape
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isPlaying) R.drawable.pause_24px
                    else R.drawable.play_arrow_24px
                ),
                null
            )
        }
    }
}