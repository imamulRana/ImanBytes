package com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
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
import androidx.media3.ui.compose.state.rememberPresentationState
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationScreenState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic.recitationItemDescription
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationPlayBackState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationPlaybackAction
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
//    val currentProgress by viewModel.currentProgress.collectAsStateWithLifecycle()
//    val currentTimeline by viewModel.audioTimeline.collectAsStateWithLifecycle()
//    val playerState by viewModel.playerState.collectAsStateWithLifecycle()
//    val currentSurahNumber by viewModel.retrieveCurrentSurahNumber.collectAsStateWithLifecycle()

    /*DisposableEffect(Unit) {
        onDispose { viewModel.persistCurrentSurah(currentSurahNumber) }
    }*/
//    val mc by viewModel.mediaController.collectAsStateWithLifecycle()
    val controller by viewModel.player.collectAsStateWithLifecycle()
    val player = rememberPresentationState(player = controller)

    RecitationTrScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = onNavigateBack,
        onNavigateToReadSurah = navigateToReadSurah,
        recitationPlayBackState = RecitationPlayBackState(),
        player = player.player
    )
}

@Composable
fun RecitationTrScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToReadSurah: (String) -> Unit = {},
    screenState: RecitationScreenState = RecitationScreenState(recitationType = RecitationType.TRANSLATION),
    recitationPlayBackState: RecitationPlayBackState,
    actions: RecitationPlaybackAction = RecitationPlaybackAction(),
    player: Player? = null
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
                title = "Recitation Translation",
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
                    recitationItemDescription(descriptionRes = R.string.recitation_description_translation)
                    recitationItemsTr(
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
                showScrollToTop = showScrollToTop
            )
            /*RecitationFloatingBar(
                modifier = Modifier.padding(innerPadding),
                onExpand = { showSheet = !showSheet },
                surah = screenState.nowPlayingSurah ?: Surah(),
                onClick = {
                    actions.playPause(screenState.nowPlayingSurah?.number.orEmpty())
                },
                playerState = recitationPlayBackState.playerState
            )*/
            /*RecitationBottomSheet(
                sheetState = sheetState,
                showSheet = showSheet,
                onSheetHide = { showSheet = false },
                playBackState = recitationPlayBackState,
                actions = actions,
                nowPlayingSurah = screenState.nowPlayingSurah,
                onReadSurahClick = onNavigateToReadSurah,
                player = player
            )*/
        }
    }
}

//todo implement player state and onclick properly
fun LazyListScope.recitationItemsTr(
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
        /*RecitationListItem(
            modifier = Modifier,
            surah = surah,
            onPlaySurah = playSurah,
            onSurahClick = onSurahClick,
            playerState = itemPlayerState
        )*/
        if (index != surahList.lastIndex)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationTrScreen(
            screenState = RecitationScreenState(
                surahList = listOf(Surah())
            ),
            recitationPlayBackState = RecitationPlayBackState()
        )
    }
}