package com.anticbyte.imanbytes.feature.quran_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.domain.model.QuranSearch
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun QuranSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: QuranSearchViewmodel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    QuranSearchScreen(modifier = modifier, state = state)
}

@Composable
fun QuranSearchScreen(
    modifier: Modifier = Modifier,
    state: QuranSearchUiState
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val searchItems = state.searchItems
                        if (searchItems != null && searchItems.results.isNotEmpty()) {
                            quranSearchResults(searchResult = searchItems)
                        } else {
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
        item(key = "surah_${surah.id}") {
            Text(
                text = "${surah.name} (${surah.transliteration})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        itemsIndexed(
            items = verses,
            key = { _, verse -> "verse_${surah.id}_${verse.id}" }
        ) { index, verse ->
            SegmentedListItem(
                onClick = {},
                shapes = ListItemDefaults.segmentedShapes(index, verses.size),
                supportingContent = {
                    Text(
                        text = verse.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                overlineContent = {
                    Text(text = "Verse ${verse.id}")
                }
            ) {
                Text(
                    text = verse.translation,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
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
                                    translation = "[All] praise is [due] to Allah, Lord of the worlds -"
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
    ImanBytesTheme {
        QuranSearchScreen(state = state)
    }
}
