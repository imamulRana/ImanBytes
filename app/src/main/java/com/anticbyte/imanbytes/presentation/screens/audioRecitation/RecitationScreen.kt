package com.anticbyte.imanbytes.presentation.screens.audioRecitation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar

@Composable
fun RecitationScreenRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    navToArRecitation: () -> Unit,
    navToTrRecitation: () -> Unit,
    navToSelfRecitation: () -> Unit
) {
    RecitationScreen(
        modifier = modifier,
        onNavigateBack = onNavigateBack,
        onNavToArRecitation = navToArRecitation,
        onNavToTrRecitation = navToTrRecitation,
        onNavToSelfRecitation = navToSelfRecitation
    )
}

@Composable
fun RecitationScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavToArRecitation: () -> Unit = {},
    onNavToTrRecitation: () -> Unit = {},
    onNavToSelfRecitation: () -> Unit = {}
) {
    Scaffold(topBar = {
        AppTopBar(
            title = "Recitation",
            isBackVisible = true,
            onNavigationIconClick = onNavigateBack
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )),
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            RecitationSelectionItem(
                modifier = modifier,
                iconRes = R.drawable.ic_recitation,
                title = "Arabic Recitation of the Holy Quran",
                subtitle = "Recitation in the voice of Mishary Rashid Alafasy in Arabic",
                itemShape = MaterialShapes.Pill.toShape(),
                onRecitationItemClick = onNavToArRecitation,
                shapes = ListItemDefaults.segmentedShapes(0, 3)
            )
            RecitationSelectionItem(
                modifier = modifier,
                iconRes = R.drawable.ic_translation,
                title = "Arabic Recitation with English Translation",
                subtitle = "Recitation in the voice of Mishary Rashid Alafasy in Arabic with English translation in the Voice of Ibrahim Walk",
                itemShape = MaterialShapes.Square.toShape(),
                onRecitationItemClick = onNavToTrRecitation,
                shapes = ListItemDefaults.segmentedShapes(1, 3)
            )
            RecitationSelectionItem(
                modifier = modifier,
                iconRes = R.drawable.ic_self_recitation,
                title = "Arabic Recitation with English Translation",
                subtitle = "Recitation in the voice of Mishary Rashid Alafasy in Arabic with English translation in the Voice of Ibrahim Walk",
                itemShape = MaterialShapes.Cookie6Sided.toShape(),
                onRecitationItemClick = onNavToSelfRecitation,
                shapes = ListItemDefaults.segmentedShapes(2, 3)
            )
        }
    }
}

@Composable
fun RecitationSelectionItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.ic_recitation,
    title: String = "Arabic Recitation",
    subtitle: String = "Arabic",
    itemShape: Shape = MaterialShapes.Pill.toShape(),
    onRecitationItemClick: () -> Unit = {},
    shapes: ListItemShapes = ListItemDefaults.segmentedShapes(0, 0)
) {
    SegmentedListItem(
        modifier = modifier,
        onClick = {
            onRecitationItemClick()
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = colorScheme.primaryContainer,
                        shape = itemShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = iconRes), null,
                    modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize),
                    tint = colorScheme.onPrimaryContainer
                )
            }
        },
        supportingContent = { Text(text = subtitle) },
        shapes = shapes,
        colors = ListItemDefaults.segmentedColors(containerColor = colorScheme.surfaceContainerLow)
    ) {
        Text(text = title)
    }
}
