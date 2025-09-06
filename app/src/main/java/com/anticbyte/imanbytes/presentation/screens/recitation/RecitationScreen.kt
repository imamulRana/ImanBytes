package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
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

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    BottomSheetScaffold(
        topBar = {
            AppTopBar(
                title = "Recitation & Translation",
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        sheetContent = {}) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding.customInnerPadding(),
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            recitationSectionHeader(
                selected = selectedRecitationType,
                onSelected = { selectedRecitationType = it }
            )
            recitationSurahList(
                surahList = state.surahList,
                currentMediaId = mediaItem,
                isPlaying = isPlaying,
                playAudio = {
                    viewModel.playSurah(
                        surah = it,
                        recitationType = selectedRecitationType
                    )
                }
            )
        }
    }
}

fun LazyListScope.recitationSectionHeader(
    modifier: Modifier = Modifier,
    selected: RecitationType = RecitationType.ARABIC,
    onSelected: (RecitationType) -> Unit

) {
    item {
        Column(
            modifier = modifier.paddingWithoutTop(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "“(it is) a Book (the Quran) which We have sent down to you, full of blessings, that they may ponder over its Verses, and that men of understanding may remember.” [Sad 38:29]",
                textAlign = TextAlign.Justify,
                style = typography.bodyLarge
            )
            FilterRecitationType(selected = selected, onSelected = onSelected)
        }
    }
}

@Composable
fun FilterRecitationType(
    modifier: Modifier = Modifier,
    selected: RecitationType = RecitationType.ARABIC,
    onSelected: (RecitationType) -> Unit
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        RecitationType.entries.forEach { recitationType ->
            val selectedType = recitationType.ordinal == selected.ordinal
            FilterChip(
                selected = selectedType,
                onClick = { onSelected(recitationType) },
                label = { Text(text = recitationType.label) },
                leadingIcon = {
                    if (selectedType) Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                        contentDescription = null
                    )
                    else null
                })
        }
    }
}

fun LazyListScope.recitationSurahList(
    surahList: List<Surah>,
    currentMediaId: String?,
    isPlaying: Boolean,
    playAudio: (surah: Surah) -> Unit
) {
    items(surahList) { surah ->
        val isThisItemPlaying = (currentMediaId == surah.number) && isPlaying
        RecitationListItem(
            surah = surah,
            isPlaying = isThisItemPlaying,
            playAudio = { playAudio(surah) }
        )
        if (surah != surahList.last()) HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationListItem(
    modifier: Modifier = Modifier,
    surah: Surah,
    isPlaying: Boolean,
    playAudio: () -> Unit
) {
    ListItem(
        modifier = modifier, leadingContent = {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.secondaryContainer, CircleShape)
                    .size(size = 56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.number,
                    color = colorScheme.onSecondaryContainer,
                    style = typography.titleMedium
                )
            }
        },
        headlineContent = { Text(surah.englishName) },
        overlineContent = { Text(surah.name) },
        supportingContent = { Text(surah.englishNameTranslation) },
        trailingContent = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "${surah.numberOfAyahs} Ayahs")
                    Spacer(Modifier.size(56.dp))
                    FilledIconToggleButton(
                        checked = isPlaying,
                        onCheckedChange = { playAudio() }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (isPlaying) R.drawable.pause_24px else R.drawable.play_arrow_24px
                            ),
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        colors = ListItemDefaults.colors(
            headlineColor = if (isPlaying) {
                colorScheme.primary
            } else ListItemDefaults.contentColor
        )
    )
}

@Composable
fun RecitationBottomSheetContent(
    modifier: Modifier = Modifier, surah: Surah
) {
    var isMediaPlaying by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier) {
        ListItem(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .clip(shape = shapes.medium),
            leadingContent = {
                Box(
                    modifier = Modifier
                        .background(color = colorScheme.primary, CircleShape)
                        .sizeIn(minWidth = 48.dp, minHeight = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = surah.number, color = colorScheme.onPrimary)
                }
            },
            headlineContent = {
                Text(
                    text = surah.englishName, modifier = Modifier.basicMarquee(), maxLines = 1
                )
            },
            supportingContent = {
                Text(text = surah.englishNameTranslation)
            },
            trailingContent = {
                //todo implement iconClick
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isMediaPlaying) R.drawable.pause_24px
                            else R.drawable.play_arrow_24px
                        ), null
                    )
                }
            },
        )

    }
}

@Composable
fun RecitationPager(modifier: Modifier = Modifier) {
    val state = rememberPagerState { RecitationType.entries.size }
    HorizontalPager(state = state) {

    }
}

fun PagerScope.pageOne(){
}

@Preview(
    showBackground = true,
)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
//        RecitationScreen()
    }
}

fun Modifier.paddingWithoutTop(paddingValues: Dp): Modifier = composed {

    padding(
        start = paddingValues, end = paddingValues, bottom = paddingValues
    )
}

fun PaddingValues.customInnerPadding() = PaddingValues(
    top = this.calculateTopPadding(),
    start = this.calculateStartPadding(LayoutDirection.Ltr),
    end = this.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding().plus(48.dp)
)