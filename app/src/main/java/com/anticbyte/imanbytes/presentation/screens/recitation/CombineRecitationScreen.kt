//package com.anticbyte.imanbytes.presentation.screens.recitation
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.spring
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyListScope
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
//import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.Wallpapers
//import androidx.compose.ui.unit.dp
//import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.anticbyte.imanbytes.domain.model.Surah
//import com.anticbyte.imanbytes.presentation.component.AppTopBar
//import com.anticbyte.imanbytes.presentation.screens.recitation.component.PlayerSeekType
//import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationBottomSheetContent
//import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingBar
//import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationListItem
//import com.anticbyte.imanbytes.presentation.screens.recitation.component.customInnerPadding
//import com.anticbyte.imanbytes.presentation.screens.recitation.component.recitationSectionHeader
//import com.anticbyte.imanbytes.theme.ImanBytesTheme
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
//@Composable
//fun CombineRecitationScreen(
//    modifier: Modifier = Modifier,
//    viewModel: RecitationViewModel = hiltViewModel<RecitationViewModel>()
//) {
//    val state by viewModel.recitationUiState.collectAsStateWithLifecycle()
//    var selectedRecitationType by rememberSaveable { mutableStateOf(RecitationType.ARABIC) }
//
//    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
//    val progress by viewModel.currentProgress.collectAsStateWithLifecycle()
//    val mediaItem by viewModel.mediaItem.collectAsStateWithLifecycle()
//    val pagerState = rememberPagerState { RecitationType.entries.size }
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//    var isSheetExpanded by rememberSaveable { mutableStateOf(false) }
//    var nowPlayingSurah by rememberSaveable { mutableStateOf("") }
//    val recitationTimeline by viewModel.recitationTimeline.collectAsStateWithLifecycle()
//    val scrollState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//
//    val showFab by remember { derivedStateOf { scrollState.firstVisibleItemIndex > 1 } }
//
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    LaunchedEffect(
//        selectedRecitationType,
//        sheetState, isSheetExpanded
//    ) {
//        pagerState.animateScrollToPage(
//            selectedRecitationType.ordinal,
//            animationSpec = tween(250)
//        )
//        if (isSheetExpanded) sheetState.show()
//        else sheetState.hide()
//    }
//    //Layout
//    Scaffold(
//        topBar = {
//            AppTopBar(
//                title = "Recitation & Translation",
//                isBackVisible = true,
//                scrollBehavior = scrollBehavior,
//                onNavigationIconClick = { isSheetExpanded = !isSheetExpanded }
//            )
//        },
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//    ) { contentPadding ->
//        HorizontalPager(
//            state = pagerState,
//            userScrollEnabled = false
//        ) {
//            Box(
//                Modifier
//                    .fillMaxSize()
//            ) {
//                LazyColumn(
//                    state = scrollState,
//                    modifier = modifier,
//                    contentPadding = contentPadding.customInnerPadding(),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    recitationSectionHeader(
//                        selected = selectedRecitationType,
//                        onSelected = { selectedRecitationType = it }
//                    )
//                    when (selectedRecitationType) {
//                        RecitationType.ARABIC -> arabicRecitation(
//                            modifier = Modifier.clickable(
//                                onClick = { isSheetExpanded = !isSheetExpanded }
//                            ),
//                            surahList = state.surahList,
//                            currentMediaId = mediaItem,
//                            isPlaying = isPlaying,
//                            playAudio = {
//                                viewModel.playSurah(it.number, selectedRecitationType)
//                                nowPlayingSurah = it.number
//                            }
//                        )
//
//                        RecitationType.TRANSLATION -> {
//                            translationRecitation(
//                                modifier = Modifier.clickable(
//                                    onClick = { isSheetExpanded = !isSheetExpanded }
//                                ),
//                                state = state,
//                                isPlaying = isPlaying,
//                                playAudio = {
//                                    viewModel.playSurah(it, selectedRecitationType)
//                                    nowPlayingSurah = it
//                                },
//                                currentMediaId = mediaItem
//                            )
//                        }
//                    }
//                }
//
//                AnimatedVisibility(
//                    showFab, modifier = Modifier
//                        .padding(contentPadding)
//                        .padding(horizontal = 16.dp)
//                        .align(Alignment.BottomCenter)
//                        .offset(y = -(ScreenOffset.plus(78.dp))),
//                    enter = slideInVertically(spring()),
//                    exit = fadeOut()
//                ) {
//                    Button(onClick = {
//                        coroutineScope.launch {
//                            scrollState.animateScrollToItem(0)
//                        }
//                    }
//                    ) {
//                        Text("Scroll to top")
//                    }
//                }
//
//                RecitationFloatingBar(
//                    modifier = Modifier
//                        .padding(contentPadding)
//                        .padding(horizontal = 16.dp)
//                        .align(Alignment.BottomCenter)
//                        .offset(y = -ScreenOffset),
//                    onClick = {
//                        viewModel.playSurah(
//                            surahNumber = nowPlayingSurah,
//                            selectedRecitationType
//                        )
//                    },
//                    isPlaying = isPlaying,
//                    state = state,
//                    onExpand = {
//                        isSheetExpanded = !isSheetExpanded
//                    }
//                )
//            }
//        }
//        if (isSheetExpanded) {
//            ModalBottomSheet(
//                modifier = Modifier.fillMaxSize(),
//                onDismissRequest = { isSheetExpanded = false },
//                sheetState = sheetState,
//                dragHandle = null
//            ) {
//                RecitationBottomSheetContent(
//                    modifier = Modifier,
//                    state = state,
//                    recitationTimeline = recitationTimeline,
//                    isPlaying = isPlaying,
//                    progress = { progress },
//                    onPlayPause = {
//                        viewModel.playSurah(
//                            surahNumber = nowPlayingSurah,
//                            recitationType = selectedRecitationType
//                        )
//                    },
//                    onSeekForward = { viewModel.seekAudio(PlayerSeekType.FORWARD) },
//                    onSeekBackward = { viewModel.seekAudio(PlayerSeekType.BACKWARD) }
//                )
//            }
//        }
//    }
//}
//
//fun LazyListScope.arabicRecitation(
//    modifier: Modifier = Modifier,
//    surahList: List<Surah>,
//    currentMediaId: String?,
//    isPlaying: Boolean,
//    playAudio: (surah: Surah) -> Unit
//) {
//    items(surahList) { surah ->
//        val isThisItemPlaying =
//            (currentMediaId == surah.number.plus(".${RecitationType.ARABIC.recitationId}")) and isPlaying
//        RecitationListItem(
//            modifier = modifier,
//            surah = surah,
//            isPlaying = isThisItemPlaying,
//            playSurah = { playAudio(surah) }
//        )
//        if (surah != surahList.last()) HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
//    }
//}
//
//@Preview(
//    showBackground = true,
//    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
//    device = "id:pixel_9a",
//    showSystemUi = true
//)
//@Composable
//private fun DefPrev() {
//    ImanBytesTheme(dynamicColor = false, darkTheme = true) {
//        CombineRecitationScreen()
//    }
//}
