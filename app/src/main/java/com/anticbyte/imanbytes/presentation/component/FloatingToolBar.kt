package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import com.anticbyte.imanbytes.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppFloatingToolBar(
    modifier: Modifier = Modifier,
    onMarkClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean,
    isMarked: Boolean
) {
    val iconFavorite = rememberVectorPainter(
        ImageVector.vectorResource(
            id =
                if (!isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_fill
        )
    )

    val iconMark = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_check))

    HorizontalFloatingToolbar(
        modifier = modifier,
        expanded = true,
        colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
        /*floatingActionButton = {
            FloatingToolbarDefaults.VibrantFloatingActionButton(onClick = {}) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.arrow_forward), null)
            }
        }
        floatingActionButtonPosition = FloatingToolbarHorizontalFabPosition.End*/
    ) {
        FilledIconToggleButton(
            checked = isMarked, onCheckedChange = { onMarkClick() },
            colors = IconButtonDefaults.filledIconToggleButtonColors(),
            shapes = IconButtonDefaults.toggleableShapes(checkedShape = CircleShape)
        ) {
            Icon(
                painter = iconMark,
                contentDescription = null
            )
        }
        FilledIconToggleButton(
            checked = isFavorite, onCheckedChange = { onFavoriteClick() },
            colors = IconButtonDefaults.filledIconToggleButtonColors(),
            shapes = IconButtonDefaults.toggleableShapes(checkedShape = CircleShape)
        ) {
            Icon(
                painter = iconFavorite,
                contentDescription = null
            )
        }
    }
}