package com.anticbyte.imaanbytes.presentation.component


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    navigationIcon: @Composable () -> Unit = {}
) {
    LargeFlexibleTopAppBar(
        title = { Text(text = title ?: "") },
        modifier = modifier,
        subtitle = { Text(text = subtitle ?: "") }, navigationIcon = navigationIcon
    )
}