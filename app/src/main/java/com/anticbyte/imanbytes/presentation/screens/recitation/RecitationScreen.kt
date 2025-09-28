package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar

@Composable
fun RecitationScreenRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    navToArRecitation: () -> Unit,
    navToTrRecitation: () -> Unit
) {
    RecitationScreen(
        modifier = modifier,
        onNavigateBack = onNavigateBack,
        onNavToArRecitation = navToArRecitation,
        onNavToTrRecitation = navToTrRecitation
    )
}

@Preview
@Composable
fun RecitationScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavToArRecitation: () -> Unit = {},
    onNavToTrRecitation: () -> Unit = {}
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
                .padding(innerPadding)
        ) {
            RecitationSelectionItem(
                modifier = modifier,
                iconRes = R.drawable.ic_recitation,
                title = "Arabic Recitation of the Holy Quran",
                subtitle = "Recitation in the voice of Mishary Rashid Alafasy in Arabic",
                itemShape = MaterialShapes.Pill.toShape(),
                onRecitationItemClick = onNavToArRecitation
            )
            RecitationSelectionItem(
                modifier = modifier,
                iconRes = R.drawable.ic_translation,
                title = "Arabic Recitation with English Translation",
                subtitle = "Recitation in the voice of Mishary Rashid Alafasy in Arabic with English translation in the Voice of Ibrahim Walk",
                itemShape = MaterialShapes.Square.toShape(),
                onRecitationItemClick = onNavToTrRecitation
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun RecitationSelectionItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.ic_recitation,
    title: String = "Arabic Recitation",
    subtitle: String = "Arabic",
    itemShape: Shape = MaterialShapes.Pill.toShape(),
    onRecitationItemClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .clickable(onClick = onRecitationItemClick),
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
        headlineContent = { Text(text = title) },
        supportingContent = { Text(text = subtitle) }
    )
}
