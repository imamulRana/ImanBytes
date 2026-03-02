package com.anticbyte.imanbytes.feature.quran_search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.QuranSearch
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun QuranSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: QuranSearchViewmodel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    QuranSearchScreen(
        modifier = modifier,
        state = state,
        textFieldState = viewModel.searchFieldState,
        onSearch = {
            viewModel.searchQuran(it)
        }
    )
}

@Composable
fun QuranSearchScreen(
    modifier: Modifier = Modifier,
    state: QuranSearchUiState,
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Search",
                subtitle = "Search for verses in the Quran, by a specific word or phrase",
                isBackVisible = true,
                onNavigationIconClick = {
                    //todo back click logic
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            when {
                state.isLoading -> AppLoader()
                state.errorMessage != null -> AppErrorScreen(
                    errorMessage = state.errorMessage,
                    onRetry = {}
                )

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                    ) {
                        val searchItems = state.searchItems
                        item {
                            TextField(
                                state = textFieldState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                shape = shapes.large,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = colorScheme.surfaceContainerLowest,
                                    unfocusedContainerColor = colorScheme.surfaceContainerLowest,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                                        null
                                    )
                                },
                                placeholder = {
                                    Text("Mercy, Faith, and Love")
                                },
                                trailingIcon = {
                                    if (textFieldState.text.isNotEmpty())
                                        IconButton(onClick = {
                                            onSearch(textFieldState.text.toString())
                                        }) {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                                                null
                                            )
                                        }
                                }

                            )
                        }
                        if (searchItems != null && searchItems.results.isNotEmpty())
                            quranSearchResults(searchResult = searchItems)
                        else {
                            item {
                                Text(
                                    text = "No results found",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun LazyListScope.quranSearchResults(searchResult: QuranSearch) {
    searchResult.results.forEach { (surah, verses) ->
        item {
            ListItem(
                onClick = {},
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_book_op20),
                        null
                    )
                }) {
                Row {
                    Text(surah.transliteration + " (${surah.translation})", maxLines = 1)
                }
            }
        }
        itemsIndexed(items = verses) { index, verse ->
            SegmentedListItem(
                onClick = {},
                shapes = ListItemDefaults.segmentedShapes(index, verses.size),
                supportingContent = {
                    Text(
                        text = verse.text
                    )
                },
                overlineContent = {
                    Text(text = "${surah.id}: ${verse.id}")
                },
                colors = ListItemDefaults.segmentedColors(
                    containerColor = colorScheme.surfaceContainerLowest
                )
            ) {
                val text = verse.translation
                val regex = searchResult.query.toRegex(RegexOption.IGNORE_CASE)
                Text(
                    text = buildAnnotatedString {
                        append(text)

                        regex.findAll(text).forEach {
                            addStyle(
                                SpanStyle(color = Color.Green, fontWeight = FontWeight.Medium),
                                start = it.range.first,
                                end = it.range.last + 1
                            )
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun SearchListItemCount(modifier: Modifier = Modifier) {
    SuggestionChip(
        icon = {
            Icon(
                ImageVector.vectorResource(R.drawable.ic_search),
                contentDescription = null
            )
        },
        onClick = {},
        label = {
            Text("143 results found for \"praise\"")
        })
}

class QuranSearchPreviewParam : PreviewParameterProvider<QuranSearchUiState> {
    override val values: Sequence<QuranSearchUiState>
        get() = sequenceOf(
            QuranSearchUiState(
                isLoading = false,
                errorMessage = null,
                searchItems = QuranSearch(
                    language = "en",
                    query = "praise",
                    results = listOf(
                        Pair(
                            QuranSearch.Surah(
                                id = 1,
                                name = "الفاتحة",
                                transliteration = "Al-Fatiha",
                                translation = "The Opening"
                            ),
                            listOf(
                                QuranSearch.Verse(
                                    id = 1,
                                    text = "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ",
                                    translation = "In the name of Allah, the Entirely Merciful, the Especially Merciful."
                                ),
                                QuranSearch.Verse(
                                    id = 2,
                                    text = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                                    translation = "[All] praise is [due] to Allah, Lord of the worlds - the merciful"
                                )
                            )
                        ),
                        Pair(
                            QuranSearch.Surah(
                                id = 2,
                                name = "الفاتحة",
                                transliteration = "Al-Baqara",
                                translation = "The Opening"
                            ),
                            listOf(
                                QuranSearch.Verse(
                                    id = 56,
                                    text = "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ",
                                    translation = "In the name of Allah, the Entirely Merciful, the Especially Merciful."
                                ),
                                QuranSearch.Verse(
                                    id = 33,
                                    text = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                                    translation = "[All] praise is [due] to Allah, Lord of the worlds - the merciful"
                                )
                            )
                        )
                    ),
                    total = 2
                )
            ),
            QuranSearchUiState(isLoading = true),
            QuranSearchUiState(errorMessage = "An unexpected error occurred")
        )
}

@Preview(showBackground = true)
@Composable
private fun QuranSearchPreview(
    @PreviewParameter(QuranSearchPreviewParam::class) state: QuranSearchUiState
) {
    ImanBytesTheme(dynamicColor = false) {
        QuranSearchScreen(state = state, textFieldState = rememberTextFieldState())
    }
}
