@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.presentation.knowledge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.KnowledgeSectionItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme

/**
 * Composable function that displays the Knowledge screen.
 * This screen provides viewmodel for managing the screen's state and logic.
 *
 * @param modifier Modifier to be applied to the root composable.
 * @param viewModel The [KnowledgeViewModel] instance for managing the screen's state and logic.
 *                  It is typically provided by Hilt.
 */
@Composable
fun KnowledgeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: KnowledgeViewModel = hiltViewModel(),
    navigateToQuran: () -> Unit
) {
    KnowledgeScreen(navigateToQuran = {}, onItemClick = {
        navigateToQuran()
    })
}

/**
 * Displays the main screen for accessing various knowledge sections.
 *
 * This screen presents a grid of [KnowledgeItem]s, allowing users to navigate to specific
 * areas of knowledge like Quran, Hadith, etc.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param navigateToQuran A callback function to navigate to the Quran section. (Currently unused in this composable directly, but kept for potential future use or consistency).
 * @param onItemClick A callback function that is invoked when a [KnowledgeItem] in the grid is clicked.
 *                    It receives the clicked [KnowledgeItem] as a parameter.
 */
@Composable
fun KnowledgeScreen(
    modifier: Modifier = Modifier,
    navigateToQuran: () -> Unit = {},
    onItemClick: (KnowledgeItem) -> Unit = {}
) {
    Scaffold(topBar = {
        AppTopBar(
            title = "Knowledge",
            isBackVisible = true,
            subtitle = null,
            onNavigationIconClick = {}
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),

            ) {
            KnowledgeItems(
                //todo implement the click functions properly
                onItemClick = onItemClick
            )
        }
    }
}

/**
 * Renders a list of knowledge items in a lazy grid.
 *
 * @param items The list of [KnowledgeItem]s to display.
 * @param onItemClick A callback function that is invoked when an item is clicked.
 */
@Composable
fun KnowledgeItems(
    onItemClick: (KnowledgeItem) -> Unit
) {
    KnowledgeItem.entries.fastForEach { item ->
        KnowledgeSectionItem(
            leadingIcon = item.iconRes,
            titleRes = item.titleRes,
            descriptionRes = item.descriptionRes,
            onItemClick = { onItemClick(item) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ImanBytesTheme(dynamicColor = false) {
        KnowledgeScreen(navigateToQuran = {})
    }
}