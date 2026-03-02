@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.presentation.knowledge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.KnowledgeSectionItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.lzColCustomPadding

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
    viewModel: KnowledgeViewModel,
    navigateToQuran: () -> Unit,
    navigateToHadith: () -> Unit,
    navigateToPillar: () -> Unit,
    navigateToAsma: () -> Unit,
    navigateToRamadan: () -> Unit,
    navigateToSearch: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    KnowledgeScreen(
        onNavigateToQuran = navigateToQuran,
        onNavigateToHadith = navigateToHadith,
        onNavigateToPillar = navigateToPillar,
        onNavigateToAsma = navigateToAsma,
        onNavigateToRamadan = navigateToRamadan,
        onNavigateToSearch = navigateToSearch,
        screenState = state
    )
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
    onNavigateToQuran: () -> Unit = {},
    onNavigateToHadith: () -> Unit = {},
    onNavigateToPillar: () -> Unit = {},
    onNavigateToAsma: () -> Unit = {},
    onNavigateToRamadan: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    screenState: KnowledgeScreenState
) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Knowledge",
                isBackVisible = false,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (screenState.isLoading) AppLoader()
            else
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(lzColCustomPadding)
                ) {
                    KnowledgeItems(
                        onNavigateToQuran = onNavigateToQuran,
                        onNavigateToHadith = onNavigateToHadith,
                        onNavigateToPillar = onNavigateToPillar,
                        onNavigateToAsma = onNavigateToAsma,
                        onNavigateToRamadan = onNavigateToRamadan,
                        onNavigateToSearch = onNavigateToSearch,
                        items = screenState.knowledgeItems
                    )
                }
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
    onNavigateToQuran: () -> Unit = {},
    onNavigateToHadith: () -> Unit = {},
    onNavigateToPillar: () -> Unit = {},
    onNavigateToAsma: () -> Unit = {},
    onNavigateToRamadan: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    items: List<KnowledgeItem> = emptyList()
) {
    items.fastForEach { item ->
        KnowledgeSectionItem(
            leadingIcon = item.iconRes,
            titleRes = item.titleRes,
            descriptionRes = item.descriptionRes,
            leadingShape = item.leadingShape.toShape(),
            onItemClick = {
                when (item) {
//                    KnowledgeItem.QURAN -> onNavigateToQuran()
                    /*KnowledgeItem.HADITH -> onNavigateToHadith()
                    KnowledgeItem.PILLARS -> onNavigateToPillar()*/
                    KnowledgeItem.ASMA -> onNavigateToAsma()
                    KnowledgeItem.RAMADAN -> onNavigateToRamadan()
//                    KnowledgeItem.DUA -> {}
                    KnowledgeItem.SEARCH -> onNavigateToSearch()
                }
            },
            shapes = ListItemDefaults.segmentedShapes(item.ordinal, KnowledgeItem.entries.size)
        )
        Spacer(Modifier.size(ListItemDefaults.SegmentedGap))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ImanBytesTheme(dynamicColor = false) {
        KnowledgeScreen(screenState = KnowledgeScreenState())
    }
}