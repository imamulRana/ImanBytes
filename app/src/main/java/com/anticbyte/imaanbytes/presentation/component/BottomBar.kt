package com.anticbyte.imaanbytes.presentation.component


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.ShortNavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imaanbytes.R

val bottomBarItems = listOf("Flashcards", "Profile")
val bottomBarIcons = listOf(R.drawable.ic_cards, R.drawable.ic_profile)

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit
) {
    ShortNavigationBar(modifier = modifier) {
        bottomBarItems.fastForEachIndexed { index, item ->
            ShortNavigationBarItem(
                selected = index == 0,
                onClick = onClick,
                icon = {
                    Icon(
                        ImageVector.vectorResource(id = bottomBarIcons[index]),
                        ""
                    )
                },
                label = { Text(text = item) },
                colors = ShortNavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.surface,
                    selectedTextColor = colorScheme.primary,
                    selectedIndicatorColor = colorScheme.primary
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
private fun AppBottomBarPreview() {
    AppBottomBar(selected = true, onClick = {})
}