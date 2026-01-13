package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationSelfListItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationSelfRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationSelfViewModel,
    navigateBack: () -> Unit,
    navigateToSurah: (String) -> Unit
) {
    val uiState by viewModel.selfRecitationUiState.collectAsStateWithLifecycle()
    RecitationSelfScreen(
        modifier = modifier,
        uiState = uiState,
        onNavigateBack = navigateBack,
        onNavigateToSurah = navigateToSurah
    )
}

@Composable
fun RecitationSelfScreen(
    modifier: Modifier = Modifier,
    uiState: RecitationSelfUiState,
    onNavigateBack: () -> Unit,
    onNavigateToSurah: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Read the Quran",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
        ) {
            if (uiState.isLoading) AppLoader()
            else if (!uiState.errorMessage.isNullOrBlank()) AppErrorScreen(errorMessage = uiState.errorMessage) { }
            else
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    ),
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    if (uiState.isLoading) loadingItem()
                    else {
                        txtRecitationItemDesc(descriptionRes = R.string.recitation_description_arabic)
                        recitationItemsSelf(
                            surahList = uiState.surahList,
                            onSurahClick = onNavigateToSurah
                        )
                    }
                }
        }
    }
}

fun LazyListScope.recitationItemsSelf(
    surahList: List<Surah>,
    onSurahClick: (String) -> Unit
) {
    itemsIndexed(surahList) { index, surah ->
        RecitationSelfListItem(
            surah = surah,
            shapes = ListItemDefaults.segmentedShapes(index, surahList.size),
            onItemClick = onSurahClick
        )
    }
}


@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
//        SurahRecitationItem()
    }
}