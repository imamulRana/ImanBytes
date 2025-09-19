package com.anticbyte.imanbytes.presentation.screens.recitation.translation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationListItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationTrRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationTrViewModel,
    onNavigateBack: () -> Unit
) {
    val screenState by viewModel.recitationUiState.collectAsStateWithLifecycle()
    RecitationTrScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationTrScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    screenState: RecitationTrScreenState = RecitationTrScreenState()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val showScrollToTop by remember {
        derivedStateOf { (listState.firstVisibleItemIndex > 1) and listState.lastScrolledBackward }
    }
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Arabic Recitation",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                contentPadding = innerPadding
            ) {
                if (screenState.isLoading) loadingItem()
                else
                    recitationItemsTr(surahList = screenState.surahList)
            }
            RecitationFloatingButton(
                innerPadding = innerPadding,
                listState = listState,
                showScrollToTop = showScrollToTop)
        }
    }
}

//todo implement player state and onclick properly
fun LazyListScope.recitationItemsTr(surahList: List<Surah>) {
    items(surahList) { surah ->
        RecitationListItem(
            modifier = Modifier,
            surah = surah,
            playerState = PlayerState.PlayerPaused,
            playSurah = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationTrScreen()
    }
}