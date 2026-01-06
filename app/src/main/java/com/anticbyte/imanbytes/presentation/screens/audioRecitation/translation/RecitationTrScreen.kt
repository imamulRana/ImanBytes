package com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppIconButton
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationScreenState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic.recitationItemDescription
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationBottomSheet
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.customInnerPadding
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@SuppressLint("VisibleForTests")
@OptIn(UnstableApi::class)
@Composable
fun RecitationTrRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationViewModel,
    onNavigateBack: () -> Unit,
    navigateToReadSurah: (String) -> Unit
) {
    val screenState by viewModel.recitationUiState.collectAsStateWithLifecycle()
    val player by viewModel.mediaControllerState.collectAsStateWithLifecycle()
    val currentSurahNumber by viewModel.currentPlayingSurah.collectAsStateWithLifecycle()
    val isSurahPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    RecitationTrScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = onNavigateBack,
        onNavigateToReadSurah = navigateToReadSurah,
        player = player,
        currentSurahNumber = currentSurahNumber,
        isPlaying = isSurahPlaying,
        togglePlayPause = { viewModel.togglePlayPause(it, RecitationType.TRANSLATION) }
    )
}

@Composable
fun RecitationTrScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToReadSurah: (String) -> Unit = {},
    screenState: RecitationScreenState = RecitationScreenState(recitationType = RecitationType.TRANSLATION),
    currentSurahNumber: String? = null,
    isPlaying: Boolean = false,
    togglePlayPause: (surahNumber: String) -> Unit,
    player: Player? = null,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val showScrollToTop by remember { derivedStateOf { (listState.firstVisibleItemIndex > 0) and listState.lastScrolledBackward } }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Arabic Recitation Translation",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior,
                actions = {
                    AppIconButton(
                        onClick = { TODO("Add search") },
                        iconRes = R.drawable.ic_search
                    )
                }
            )
        }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                contentPadding = innerPadding.customInnerPadding(),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
            ) {
                if (screenState.isLoading) loadingItem()
                else {
                    recitationItemDescription(descriptionRes = R.string.recitation_description_translation)
                    recitationItemsTr(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        surahList = screenState.surahList,
                        onSurahClick = { surahNumber -> showSheet = true },
                        currentSurahNumber = currentSurahNumber,
                        isPlaying = isPlaying,
                        togglePlayPause = togglePlayPause
                    )
                }
            }
            RecitationFloatingButton(
                innerPadding = innerPadding,
                listState = listState,
                showScrollToTop = showScrollToTop
            )
            RecitationFloatingBar(
                modifier = Modifier.padding(innerPadding),
                onExpand = { showSheet = !showSheet },
                surah = screenState.nowPlayingSurah ?: Surah(),
                player = player
            )
            RecitationBottomSheet(
                modifier = Modifier,
                sheetState = sheetState,
                showSheet = showSheet,
                onSheetHide = { showSheet = false },
                onReadSurahClick = onNavigateToReadSurah,
                player = player,
                nowPlayingSurah = screenState.nowPlayingSurah
            )
        }
    }
}

//todo implement player state and onclick properly
fun LazyListScope.recitationItemsTr(
    modifier: Modifier,
    surahList: List<Surah>,
    onSurahClick: (surahNumber: String) -> Unit = {},
    currentSurahNumber: String?,
    isPlaying: Boolean,
    togglePlayPause: (surahNumber: String) -> Unit = {}
) {
    itemsIndexed(surahList) { index, surah ->
        RecitationListItem(
            modifier = modifier,
            surah = surah,
            onSurahClick = { onSurahClick(surah.number) },
            shapes = ListItemDefaults.segmentedShapes(index, surahList.size),
            togglePlayPause = togglePlayPause,
            currentSurahNumber = currentSurahNumber,
            isPlaying = isPlaying
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        /*RecitationTrScreen(
            screenState = RecitationScreenState(
                surahList = listOf(Surah())
            ),
            recitationPlayBackState = RecitationPlayBackState()
        )*/
    }
}