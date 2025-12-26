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
import androidx.compose.material3.ListItemDefaults
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
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppIconButton
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationScreenState
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationBottomSheet
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem
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
    val player by viewModel.player.collectAsStateWithLifecycle()

    RecitationArScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = navigateBack,
        onNavigateToReadSurah = navigateToReadSurah,
        /* recitationPlayBackState = RecitationPlayBackState(
             surahNumber = currentSurahNumber,
             currentTime = currentTimeline.first,
             duration = currentTimeline.second,
             progress = currentProgress,
             playerState = playerState
         ),*/
        player = player
    )
}

@OptIn(UnstableApi::class)
@Composable
fun RecitationArScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToReadSurah: (String) -> Unit = {},
    player: Player?,
    screenState: RecitationScreenState = RecitationScreenState()
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
                    recitationItemDescription(descriptionRes = R.string.recitation_description_arabic)
                    player?.let { audioPlayer ->
                        recitationItemsAr(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            surahList = screenState.surahList,
                            onSurahClick = { surahNumber ->
                                showSheet = true
                                audioPlayer.setMediaItem(MediaItem.fromUri("https://cdn.islamic.network/quran/audio/128/ar.alafasy/4125.mp3"))
                            },
                            player = audioPlayer
                        )
                    }
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
                playerState = playerState
            )*/
            if (player != null)
                RecitationBottomSheet(
                    modifier = Modifier,
                    sheetState = sheetState,
                    showSheet = showSheet,
                    onSheetHide = { showSheet = false },
                    nowPlayingSurah = screenState.nowPlayingSurah,
                    onReadSurahClick = onNavigateToReadSurah,
                    player = player
                )
        }
    }
}

// FIXME: - Implement and onclick to surah properly
@OptIn(UnstableApi::class)
fun LazyListScope.recitationItemsAr(
    modifier: Modifier,
    surahList: List<Surah>,
    onSurahClick: (surahNumber: String) -> Unit = {},
    player: Player
) {
    itemsIndexed(surahList) { index, surah ->
        RecitationListItem(
            modifier = modifier,
            surah = surah,
            onSurahClick = { onSurahClick(surah.number) },
            player = player,
            shapes = ListItemDefaults.segmentedShapes(index, surahList.size)
        )
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