package com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
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
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationScreenState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationBottomSheet
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationPlayBackState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationPlaybackAction
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.customInnerPadding
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.paddingWithoutTop
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationArRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationViewModel,
    navigateBack: () -> Unit,
    navigateToReadSurah: (String) -> Unit
) {
    val screenState by viewModel.recitationUiState.collectAsStateWithLifecycle()
    val currentProgress by viewModel.currentProgress.collectAsStateWithLifecycle()
    val currentTimeline by viewModel.audioTimeline.collectAsStateWithLifecycle()
    val playerState by viewModel.playerState.collectAsStateWithLifecycle()
    val currentSurahNumber by viewModel.retrieveCurrentSurahNumber.collectAsStateWithLifecycle()

    /*DisposableEffect(Unit) {
        onDispose { viewModel.persistCurrentSurah(currentSurahNumber) }
    }*/

    RecitationArScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = navigateBack,
        onNavigateToReadSurah = navigateToReadSurah,
        recitationPlayBackState = RecitationPlayBackState(
            surahNumber = currentSurahNumber,
            currentTime = currentTimeline.first,
            duration = currentTimeline.second,
            progress = currentProgress,
            playerState = playerState
        ),
        actions = viewModel.playerActions
    )
}

@Composable
fun RecitationArScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToReadSurah: (String) -> Unit = {},
    screenState: RecitationScreenState = RecitationScreenState(recitationType = RecitationType.ARABIC),
    recitationPlayBackState: RecitationPlayBackState,
    actions: RecitationPlaybackAction = RecitationPlaybackAction(),
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val showScrollToTop by remember { derivedStateOf { (listState.firstVisibleItemIndex > 0) and listState.lastScrolledBackward } }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    //In order to persist the current surah number when user navigates up.
    /*val context = LocalContext.current
    context.startAudioService()*/

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
                        playSurah = { surahNumber ->
                            actions.playPause(surahNumber)
                            actions.persistCurrentSurah(surahNumber)
                        },
                        onSurahClick = { surahNumber ->
                            showSheet = true
                            actions.playPause(surahNumber)
                            actions.persistCurrentSurah(surahNumber)
                        },
                        currentSurah = screenState.nowPlayingSurah ?: Surah()
                    )
                }
            }
            RecitationFloatingButton(
                innerPadding = innerPadding,
                listState = listState,
                showScrollToTop = showScrollToTop,
                color = ButtonDefaults.buttonColors(
                    if (screenState.sectionColor) colorScheme.error else colorScheme.secondary
                )
            )
            RecitationFloatingBar(
                modifier = Modifier.padding(innerPadding),
                onExpand = { showSheet = !showSheet },
                surah = screenState.nowPlayingSurah ?: Surah(),
                onClick = {
                    actions.playPause(screenState.nowPlayingSurah?.number.orEmpty())
                },
                playerState = recitationPlayBackState.playerState
            )
            RecitationBottomSheet(
                sheetState = sheetState,
                showSheet = showSheet,
                onSheetHide = { showSheet = false },
                playBackState = recitationPlayBackState,
                actions = actions,
                nowPlayingSurah = screenState.nowPlayingSurah,
                onReadSurahClick = onNavigateToReadSurah
            )
        }
    }
}

// FIXME: - Implement and onclick to surah properly
fun LazyListScope.recitationItemsAr(
    surahList: List<Surah>,
    playSurah: (surahNumber: String) -> Unit = {},
    playerState: PlayerState = PlayerState.PlayerIdle,
    onSurahClick: (surahNumber: String) -> Unit = {},
    currentSurah: Surah,
) {
    itemsIndexed(surahList) { index, surah ->
        val isCurrent = currentSurah.number == surah.number
        val itemPlayerState = when {
            isCurrent -> playerState
            else -> PlayerState.PlayerIdle
        }
        RecitationListItem(
            modifier = Modifier,
            surah = surah,
            onPlaySurah = playSurah,
            onSurahClick = onSurahClick,
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
            screenState = RecitationScreenState(
                surahList = listOf(Surah())
            ),
            recitationPlayBackState = RecitationPlayBackState()
        )
    }
}