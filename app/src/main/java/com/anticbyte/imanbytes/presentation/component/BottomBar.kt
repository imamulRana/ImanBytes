package com.anticbyte.imanbytes.presentation.component


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.anticbyte.imanbytes.navigation.NavigationBarItem

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    currentRoute: NavDestination?,
    onItemSelected: (Any?) -> Unit = {}
) {
    ShortNavigationBar(modifier = modifier, containerColor = colorScheme.background) {
        NavigationBarItem.entries.fastForEachIndexed { index, navigationBarItem ->
            val isSelected =
                currentRoute?.hierarchy?.any { it.route == navigationBarItem.navRoute::class.qualifiedName } == true
            ShortNavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(navigationBarItem.navRoute) },
                icon = {
                    Icon(
                        ImageVector.vectorResource(
                            id = if (isSelected) navigationBarItem.selectedIcon else navigationBarItem.unselectedIcon
                        ), contentDescription = null
                    )
                },
                label = { Text(text = navigationBarItem.itemLabel) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
private fun AppBottomBarPreview() {
//    AppBottomBar()
}