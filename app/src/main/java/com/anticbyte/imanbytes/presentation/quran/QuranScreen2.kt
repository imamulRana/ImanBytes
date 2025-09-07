@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.presentation.quran

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7A
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppFloatingToolBar
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.ErrorScreen
import com.anticbyte.imanbytes.utils.QuranDataFake.quranData
import com.anticbyte.imanbytes.utils.ScreenUiState

@Composable
fun QuranScreen2(
    modifier: Modifier = Modifier,
    navigateToProfile: () -> Unit = {},
    onNavigationIconClick: () -> Unit = {},
    quranViewModel: QuranViewModel
) {
    val audioUrl = "https://cdn.islamic.network/quran/audio/128/ar.alafasy/4125.mp3"
    val pagerState = rememberPagerState(pageCount = { 2 })
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    val screenQuranUiState by quranViewModel.quranState.collectAsStateWithLifecycle()

    var isFavorite by remember { mutableStateOf(false) }
    var isMarked by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val (floatingActionButton, onFloatingActionButtonClick) = remember { mutableStateOf(false) }


    val isPlaying by quranViewModel.isPlaying.collectAsStateWithLifecycle()
    val currentPosition by quranViewModel.currentProgress.collectAsStateWithLifecycle()
    val networkStatus by quranViewModel.networkStatus.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Quran $networkStatus",
                onNavigationIconClick = onNavigationIconClick,
                scrollBehavior = scrollBehavior,
                isBackVisible = true,
                actions = {
                    IconButton(onClick = {
//                        if (!isPlaying) quranViewModel.playQuranAudio(audioUrl) else quranViewModel.pauseQuranAudio()
                    }) {
                        Box(Modifier, contentAlignment = Alignment.Center) {
                            CircularWavyProgressIndicator(
                                progress = { currentPosition },
                                trackStroke = Stroke(0f),
                                stroke = Stroke(5f)
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (isPlaying) R.drawable.pause_24px else
                                        R.drawable.play_arrow_24px
                                ),
                                null
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (pagerState.currentPage == 0 && screenQuranUiState is ScreenUiState.Success) {
                FloatingActionButtonMenu(floatingActionButton, button = {
                    ToggleFloatingActionButton(
                        checked = floatingActionButton,
                        onCheckedChange = { onFloatingActionButtonClick(!floatingActionButton) }) {
                        Icon(ImageVector.vectorResource(R.drawable.ic_check), null)
                    }
                }) {
                    FloatingActionButtonMenuItem(onClick = {
                        Log.d("CurrentPosition", "QuranScreen: $currentPosition")
                    }, text = {
                        Text("Text")
                    }, icon = {
                        Icon(ImageVector.vectorResource(R.drawable.ic_check), null)
                    })


                    FloatingActionButtonMenuItem(onClick = {
                    }, text = {
                        Text("Image")
                    }, icon = {
                        Icon(ImageVector.vectorResource(R.drawable.ic_check), null)
                    })

                }
            }
        }
    ) {
        when (screenQuranUiState) {
            ScreenUiState.Loading -> AppLoader(modifier = modifier.padding(it))
            is ScreenUiState.Success -> {
                Box(modifier = modifier.padding(it)) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSpacing = 16.dp,
                        verticalAlignment = Alignment.Top,
                    ) { page ->
                        when (page) {
                            0 -> VerseScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 24.dp),
                                verseData = quranData
                            )

                            1 -> TafsirScreen(
                                modifier = Modifier
                                    .verticalScroll(scrollState)
                                    .padding(bottom = 300.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                    if (pagerState.currentPage != 0)
                        AppFloatingToolBar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y = -ScreenOffset),
                            onMarkClick = { isMarked = !isMarked },
                            onFavoriteClick = { isFavorite = !isFavorite },
                            isFavorite = isFavorite,
                            isMarked = isMarked
                        )
                }
            }

            is ScreenUiState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .padding(it)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = PIXEL_7A)
@Composable
private fun FlashCardScreenPreview() {
    ImanBytesTheme {
//        ScreenQuran()
    }
}