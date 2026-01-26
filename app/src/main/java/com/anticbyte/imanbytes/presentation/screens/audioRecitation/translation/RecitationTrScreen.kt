package com.anticbyte.imanbytes.presentation.screens.audioRecitation.translation

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppIconButton
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.player.PlayerViewModel
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.RecitationType
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic.recitationItemDescription
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(UnstableApi::class)
@Composable
fun RecitationTrRoute(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel,
    screenViewModel: RecitationTrViewModel,
    onNavigateBack: () -> Unit,
    navigateToReadSurah: (String) -> Unit
) {
    val screenState by screenViewModel.recitationUiState.collectAsStateWithLifecycle()
    val player by playerViewModel.controller.collectAsStateWithLifecycle()
    RecitationTrScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = onNavigateBack,
        onNavigateToReadSurah = navigateToReadSurah,
        player = player,
        playSurah = {
            playerViewModel.onPlay(
                surahList = screenState.surahList,
                recitationId = RecitationType.TRANSLATION.recitationId,
                surahNumber = it
            )
        }
    )
}

@Composable
fun RecitationTrScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onNavigateToReadSurah: (String) -> Unit = {},
    screenState: RecitationTrScreenState = RecitationTrScreenState(),
    isPlaying: Boolean = false,
    playSurah: (surahNumber: String) -> Unit,
    player: Player? = null,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val showScrollToTop by remember { derivedStateOf { (listState.firstVisibleItemIndex > 0) and listState.lastScrolledBackward } }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Recitation & Meaning",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(bottom = 64.dp)
    ) { innerPadding ->
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
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    recitationItemDescription(descriptionRes = R.string.recitation_description_translation)
                    recitationItemsTr(
                        surahList = screenState.surahList,
                        isPlaying = isPlaying,
                        currentSurahNumber = screenState.currentSurahNumber,
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

//todo implement player state and onclick properly
fun LazyListScope.recitationItemsTr(
    modifier: Modifier = Modifier,
    surahList: List<Surah>,
    currentSurahNumber: String?,
    isPlaying: Boolean,
    onPlaySurah: (surahNumber: String) -> Unit = {}
) {
    itemsIndexed(surahList) { index, surah ->
        RecitationListItem(
            modifier = modifier,
            surah = surah,
            onPlaySurah = onPlaySurah,
            shapes = ListItemDefaults.segmentedShapes(index, surahList.size),
            currentSurahNumber = currentSurahNumber,
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