package com.anticbyte.imanbytes.presentation.screens.recitation

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.ScreenUiState

fun <T> ScreenUiState<T>.dataOrNull(): T? =
    (this as? ScreenUiState.Success)?.data


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationScreen(
    modifier: Modifier = Modifier,
    state: ScreenUiState<RecitationState> = ScreenUiState.Success(data = RecitationState())
) {
//    val state by viewModel.recitationUiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    rememberLazyListState()
    BottomSheetScaffold(topBar = {
        AppTopBar(
            title = "Recitation & Translation",
            isBackVisible = true,
            scrollBehavior = scrollBehavior
        )
    }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), sheetContent = {
        RecitationBottomSheetContent(
            surah = Surah(
                number = "1",
                englishName = "Al-Fatiha",
                englishNameTranslation = "Al-Fatiha",
                name = "Al-Fatiha",
                numberOfAyahs = "7",
                revelationType = "Meccan"
            )
        )
    }, sheetPeekHeight = 128.dp) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding.customInnerPadding(),
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (state) {
                is ScreenUiState.Error -> {}
                ScreenUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            ContainedLoadingIndicator()
                        }
                    }
                }

                is ScreenUiState.Success -> {
                    val data = state.data
                    recitationSectionHeader()
                    recitationSurahList(state = data)
                }
            }
        }
    }
}

enum class RecitationType(
    val tabLabel: String,
) {
    ARABIC(
        tabLabel = "Recitation",
    ),
    TRANSLATION(
        tabLabel = "Recitation with Translation",
    )
}

fun PaddingValues.customInnerPadding() = PaddingValues(
    top = this.calculateTopPadding(),
    start = this.calculateStartPadding(LayoutDirection.Ltr),
    end = this.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding().plus(48.dp)
)

fun LazyListScope.recitationSectionHeader(modifier: Modifier = Modifier) {
    item {
        Column(modifier = modifier.paddingWithoutTop(16.dp)) {
            Text(
                "“(it is) a Book (the Quran) which We have sent down to you, full of blessings, that they may ponder over its Verses, and that men of understanding may remember.” [Sad 38:29]",
                textAlign = TextAlign.Justify,
                style = typography.bodyLarge
            )
        }
    }
}

fun LazyListScope.recitationSurahList(
    state: RecitationState, onSurahClick: (surahId: Int) -> Unit = {}
) {
    items(state.surahList.size) {
        RecitationListItem(
            recitationListItem = state.surahList[it], onSurahClick = onSurahClick
        )
    }
}

/*@Composable
fun RecitationListItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
    recitationListItem: Surah,
) {
    ElevatedCard(
        modifier = modifier.aspectRatio(1 / 1f), onClick = onItemClick, shape = shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .sizeIn(minWidth = 24.dp, minHeight = 24.dp)
                        .background(color = colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = recitationListItem.number,
                        style = typography.labelSmall,
                        color = colorScheme.onPrimary
                    )
                }
                Text(
                    text = recitationListItem.englishName,
                    style = typography.titleMedium,
                )
                Text(
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE),
                    text = recitationListItem.englishNameTranslation,
                    style = typography.bodySmall,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier.align(Alignment.End),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${recitationListItem.numberOfAyahs} Ayahs",
                    style = typography.labelSmall,
                )
                Text(
                    text = recitationListItem.name,
                    style = typography.labelSmall,
                )
            }
        }
    }
}*/

@Composable
fun RecitationListItem(
    modifier: Modifier = Modifier,
    recitationListItem: Surah,
    onSurahClick: (surahId: Int) -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .clickable(onClick = { onSurahClick(recitationListItem.number.toInt()) }),
        leadingContent = {
            Box(
                modifier = Modifier
                    .background(color = colorScheme.primary, CircleShape)
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(recitationListItem.number, color = colorScheme.onPrimary)
            }
        },
        headlineContent = {
            Text(recitationListItem.englishName)
        },
        overlineContent = { Text(recitationListItem.name) },
        supportingContent = {
            Text(recitationListItem.englishNameTranslation)
        },
        trailingContent = {
            Text(text = "${recitationListItem.numberOfAyahs} Ayahs")
        }
    )

}

@Preview(
    showBackground = true, device = "id:pixel_5", showSystemUi = false
)
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationScreen()
    }
}

@Composable
fun RecitationBottomSheetContent(
    modifier: Modifier = Modifier,
    surah: Surah
) {
    var isMediaPlaying by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier) {
        ListItem(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .clip(shape = shapes.medium),
            leadingContent = {
                Box(
                    modifier =
                        Modifier
                            .background(color = colorScheme.primary, CircleShape)
                            .sizeIn(minWidth = 48.dp, minHeight = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = surah.number, color = colorScheme.onPrimary)
                }
            },
            headlineContent = {
                Text(
                    text = surah.englishName,
                    modifier = Modifier.basicMarquee(),
                    maxLines = 1
                )
            },
            trailingContent = {
                //todo implement iconClick
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isMediaPlaying)
                                R.drawable.pause_24px
                            else R.drawable.play_arrow_24px
                        ),
                        null
                    )
                }
            },
        )

    }
}

fun Modifier.paddingWithoutTop(paddingValues: Dp): Modifier = composed {
    padding(
        start = paddingValues,
        end = paddingValues,
        bottom = paddingValues
    )

}