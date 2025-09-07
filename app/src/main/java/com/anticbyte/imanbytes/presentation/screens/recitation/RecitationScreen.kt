package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationListItem
import com.anticbyte.imanbytes.presentation.screens.recitation.component.customInnerPadding
import com.anticbyte.imanbytes.presentation.screens.recitation.component.recitationSectionHeader
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationScreen(
    modifier: Modifier = Modifier,
    viewModel: RecitationViewModel = viewModel()
) {
    val state by viewModel.recitationUiState.collectAsStateWithLifecycle()
    var selectedRecitationType by rememberSaveable { mutableStateOf(RecitationType.ARABIC) }

    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val mediaItem by viewModel.mediaItem.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { RecitationType.entries.size }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    LaunchedEffect(pagerState, selectedRecitationType) {
        pagerState.animateScrollToPage(
            selectedRecitationType.ordinal,
            animationSpec = tween(250)
        )
    }
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Recitation & Translation",
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { contentPadding ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {
            LazyColumn(
                modifier = modifier,
                contentPadding = contentPadding.customInnerPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                recitationSectionHeader(
                    selected = selectedRecitationType,
                    onSelected = { selectedRecitationType = it }
                )
                when (selectedRecitationType) {
                    RecitationType.ARABIC -> arabicRecitation(
                        surahList = state.surahList,
                        currentMediaId = mediaItem,
                        isPlaying = isPlaying,
                        playAudio = {
                            viewModel.playSurah(it.number, selectedRecitationType)
                        }
                    )

                    RecitationType.TRANSLATION -> {
                        translationRecitation(
                            state = state,
                            isPlaying = isPlaying,
                            playAudio = {
                                viewModel.playSurah(it, selectedRecitationType)
                            },
                            currentMediaId = mediaItem
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.arabicRecitation(
    modifier: Modifier = Modifier,
    surahList: List<Surah>,
    currentMediaId: String?,
    isPlaying: Boolean,
    playAudio: (surah: Surah) -> Unit
) {
    items(surahList) { surah ->
        val isThisItemPlaying =
            (currentMediaId == surah.number.plus(".${RecitationType.ARABIC.recitationId}")) and isPlaying
        RecitationListItem(
            surah = surah,
            isPlaying = isThisItemPlaying,
            playAudio = { playAudio(surah) }
        )
        if (surah != surahList.last()) HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationScreen()
    }
}
