package com.anticbyte.imanbytes.presentation.screens.recitation.arabic

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationBottomSheet
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingBar
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationListItem
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationPlayBackActions
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationPlayBackState
import com.anticbyte.imanbytes.presentation.screens.recitation.component.customInnerPadding
import com.anticbyte.imanbytes.presentation.screens.recitation.component.paddingWithoutTop
import com.anticbyte.imanbytes.presentation.screens.recitation.component.sampleActions
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationArRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationArViewModel,
    navigateBack: () -> Unit
) {
    val screenState by viewModel.recitationUiState.collectAsStateWithLifecycle()
    val currentProgress by viewModel.currentProgress.collectAsStateWithLifecycle()
    val currentTimeline by viewModel.audioTimeline.collectAsStateWithLifecycle()
    val playerState by viewModel.playerState.collectAsStateWithLifecycle()
    RecitationArScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = navigateBack,
        recitationPlayBackState = RecitationPlayBackState(
            surahNumber = screenState.nowPlayingSurah?.number.orEmpty(),
            currentTime = currentTimeline.first,
            duration = currentTimeline.second,
            progress = currentProgress,
            playerState = playerState
        ),
        actions = viewModel
    )
}

@Composable
fun RecitationArScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    screenState: RecitationArScreenState = RecitationArScreenState(),
    recitationPlayBackState: RecitationPlayBackState,
    actions: RecitationPlayBackActions,
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
                title = "Arabic Recitation",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                contentPadding = innerPadding.customInnerPadding()
            ) {
                if (screenState.isLoading) loadingItem()
                else {
                    recitationItemDescription(descriptionRes = R.string.recitation_description_arabic)
                    recitationItemsAr(
                        playerState = recitationPlayBackState.playerState,
                        surahList = screenState.surahList,
                        playSurah = { surahNumber -> actions.onPlayPause(surahNumber) },
                        nowPlayingSurah = screenState.nowPlayingSurah
                    )
                }
            }
            RecitationFloatingButton(
                modifier = Modifier,
                innerPadding = innerPadding,
                listState = listState,
                showScrollToTop = showScrollToTop,
            )
            RecitationFloatingBar(modifier = Modifier.padding(innerPadding), onExpand = {
                showSheet = !showSheet
            })
            RecitationBottomSheet(
                sheetState = sheetState,
                showSheet = showSheet,
                onSheetHide = { showSheet = false },
                playBackState = recitationPlayBackState,
                actions = actions
            )
        }
    }
}

// FIXME: - Implement player state and onclick properly
fun LazyListScope.recitationItemsAr(
    surahList: List<Surah>,
    playSurah: (surahNumber: String) -> Unit = {},
    playerState: PlayerState = PlayerState.PlayerIdle,
    onSurahClick: (surahNumber: String) -> Unit = {},
    nowPlayingSurah: Surah? = null
) {
    itemsIndexed(surahList) { index, surah ->
        val isCurrent = nowPlayingSurah?.number == surah.number
        val itemPlayerState = when {
            isCurrent -> playerState
            else -> PlayerState.PlayerIdle
        }
        RecitationListItem(
            modifier = Modifier,
            surah = surah,
            playSurah = playSurah,
            onItemClick = onSurahClick,
            playerState = itemPlayerState
        )
        if (index != surahList.lastIndex)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

fun LazyListScope.recitationItemDescription(@StringRes descriptionRes: Int) {
    item {
        Text(
            text = stringResource(descriptionRes),
            textAlign = TextAlign.Justify,
            modifier = Modifier.paddingWithoutTop(16.dp)
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_5,navigation=buttons")
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationArScreen(
            screenState = RecitationArScreenState(
                surahList = listOf(Surah())
            ),
            recitationPlayBackState = RecitationPlayBackState(),
            actions = sampleActions
        )
    }
}