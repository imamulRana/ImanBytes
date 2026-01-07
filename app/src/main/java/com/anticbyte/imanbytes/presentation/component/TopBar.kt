@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.presentation.component


import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@ExperimentalMaterial3Api
@Composable
fun AppTopBar(
    title: String? = null,
    subtitle: String? = null,
    onNavigationIconClick: () -> Unit = {},
    isBackVisible: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    MediumFlexibleTopAppBar(
        title = { Text(text = title ?: "") },
        subtitle = { if (subtitle != null) Text(text = subtitle) },
        navigationIcon = {
            if (isBackVisible) FilledTonalIconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back), null)
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
        colors = colors
    )
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme(darkTheme = true, dynamicColor = false) {
        AppTopBar(title = "Preview", isBackVisible = true)
    }
}