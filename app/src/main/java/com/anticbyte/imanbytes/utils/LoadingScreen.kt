@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.anticbyte.imanbytes.presentation.component.AppLoader

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        AppLoader(modifier = modifier)
    }
}

fun LazyListScope.loadingItem() {
    item {
        Box(modifier = Modifier.fillParentMaxSize()) {
            AppLoader()
        }
    }
}