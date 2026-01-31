package com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic

import androidx.annotation.OptIn
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.SearchDialog
import com.anticbyte.imanbytes.presentation.player.PlayerViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.lzColCustomPadding
import kotlinx.coroutines.launch

@Composable
fun RecitationArRoute(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel,
    viewModel: RecitationArViewModel,
    navigateBack: () -> Unit,
    navigateToReadSurah: (String) -> Unit
) {
    val screenState by viewModel.recitationUiState.collectAsStateWithLifecycle()
    val currentSurahNumber by viewModel.currentPlayingSurah.collectAsStateWithLifecycle()

    RecitationArScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = navigateBack,
        currentSurahNumber = currentSurahNumber,
        playSurah = {
            playerViewModel.playSurah(
                recitationId = RecitationType.ARABIC.recitationId,
                surahNumber = it
            )
        }
    )
}

@OptIn(UnstableApi::class)
@Composable
fun RecitationArScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    screenState: RecitationArScreenState = RecitationArScreenState(),
    currentSurahNumber: String? = null,
    playSurah: (surahNumber: String) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val showScrollToTop by remember { derivedStateOf { (listState.firstVisibleItemIndex > 0) and listState.lastScrolledBackward } }
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Quran Recitation",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {
                        isExpanded = true
                        scope.launch {
                            searchBarState.animateToExpanded()
                        }
                    }) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_search), null)
                    }
                }
            )
            SearchDialog(
                isExpanded = isExpanded,
                textFieldState = textFieldState,
                onPlaySurah = playSurah,
                onBack = {
                    isExpanded = false
                },
                surahList = screenState.surahList.filter { surah ->
                    surah.englishName.contains(textFieldState.text, ignoreCase = true)
                }
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (screenState.isLoading) AppLoader()
            else if (!screenState.errorMessages.isNullOrBlank()) AppErrorScreen(errorMessage = screenState.errorMessages) { }
            else
                LazyColumn(
                    state = listState,
                    contentPadding = lzColCustomPadding,
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    recitationItemDescription(descriptionRes = R.string.recitation_description_arabic)
                    recitationItemsAr(
                        surahList = screenState.surahList,
                        currentSurahNumber = currentSurahNumber,
                        onPlaySurah = playSurah
                    )
                }
            RecitationFloatingButton(
                innerPadding = innerPadding,
                listState = listState,
                showScrollToTop = showScrollToTop
            )
        }
    }
}

// FIXME: - Implement and onclick to surah properly
@OptIn(UnstableApi::class)
fun LazyListScope.recitationItemsAr(
    modifier: Modifier = Modifier,
    surahList: List<Surah>,
    currentSurahNumber: String?,
    onPlaySurah: (surahNumber: String) -> Unit = {}
) {
    itemsIndexed(surahList) { index, surah ->
        RecitationListItem(
            modifier = modifier,
            surah = surah,
            onPlaySurah = onPlaySurah,
            shapes = ListItemDefaults.segmentedShapes(index, surahList.size),
            currentSurahNumber = currentSurahNumber
        )
    }
}

fun LazyListScope.recitationItemDescription(@StringRes descriptionRes: Int) {
    item {
        Text(
            text = stringResource(descriptionRes),
            textAlign = TextAlign.Justify,
            style = typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_5,navigation=buttons")
@Composable
private fun DefPrev() {
    ImanBytesTheme {
//        RecitationArScreen(
//            screenState = RecitationArScreenState(
//                surahList = listOf(
//                    Surah(
//                        number = "1",
//                        name = "Al Fatiha",
//                        englishName = "Al Fatiha",
//                    )
//                )
//            ),
//            player = null
//        )
    }
}