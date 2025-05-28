package com.anticbyte.imaanbytes.presentation.component

import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun AppLoader(modifier: Modifier = Modifier) {
    ContainedLoadingIndicator(
        polygons = listOf(
            MaterialShapes.Pill,
            MaterialShapes.Oval,
            MaterialShapes.Clover4Leaf
        )
    )
}

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Preview
@Composable
private fun AppLoaderPreview() {

}