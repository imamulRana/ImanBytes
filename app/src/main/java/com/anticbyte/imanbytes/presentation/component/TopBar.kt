package com.anticbyte.imanbytes.presentation.component


import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.anticbyte.imanbytes.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@ExperimentalMaterial3Api
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    onNavigationIconClick: () -> Unit = {},
    isBackVisible: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    MediumFlexibleTopAppBar(
        title = { Text(text = title ?: "") },
        subtitle = { Text(text = subtitle ?: "") },
        modifier = modifier,
        navigationIcon = {
            if (isBackVisible)
                IconButton(onClick = onNavigationIconClick) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back), null)
                }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background)
    )
}