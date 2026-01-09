package com.anticbyte.imanbytes.presentation.random_verse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.Tafsir
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.TitleAndContentSection
import com.anticbyte.imanbytes.presentation.random_verse.component.MetaChip
import com.anticbyte.imanbytes.presentation.random_verse.component.VerseMetaChip
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun RandomVerseScreenRoute(
    viewModel: RandomVerseViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    RandomVerseScreen(
        uiState = uiState,
        onNavigateUp = navigateUp
    )
}

@Composable
fun RandomVerseScreen(
    uiState: RandomVerseScreenScreenState,
    onNavigateUp: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Random Verse",
                isBackVisible = true,
                onNavigationIconClick = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) AppLoader()
            else if (uiState.errorMessage != null) AppErrorScreen(errorMessage = uiState.errorMessage) { }
            else
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    verseText(uiState.verse)
                    item { HorizontalDivider() }
                    tafsirText(uiState.tafsir)
                    item { HorizontalDivider() }
                    item {
                        TitleAndContentSection(title = "Tafsir Reference") {
                            Text(
                                uiState.tafsir.tafsirs.lastOrNull()?.author.orEmpty(),
                                style = typography.titleMedium
                            )
                        }
                    }
                    item {
                        TitleAndContentSection(title = "Verse Translation") {
                            Text(uiState.verse.edition.englishName, style = typography.titleMedium)
                        }
                    }
                }
        }
    }
}

fun LazyListScope.verseText(verse: RandomVerse) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TitleSec(verse = verse)
            TitleAndContentSection(
                modifier = Modifier,
                title = "VERSE ${verse.numberInSurah}"
            ) {
                Text(verse.text)
            }
        }
    }
}

fun formatTextToParagraphs(
    text: String,
    sentencesPerParagraph: Int = 3
): List<String> {
    if (text.isBlank()) return emptyList()

    val sentences = text.split(Regex("(?<=[.!?])\\s+")).filter { it.isNotBlank() }

    return sentences.chunked(sentencesPerParagraph) { it.joinToString(" ") }
}

fun LazyListScope.tafsirText(tafsir: Tafsir) {
    val content = tafsir.tafsirs.lastOrNull()?.content.orEmpty()
    val paragraphs = formatTextToParagraphs(content)

    if (paragraphs.isEmpty()) return
    // Emit Title once
    item {
        TitleAndContentSection(title = "Tafsir") {
            paragraphs.forEach { para ->
                Text(
                    text = para,
                    textAlign = TextAlign.Justify,
                    style = typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun TitleSec(
    modifier: Modifier = Modifier,
    verse: RandomVerse = RandomVerse()
) {
    val metaChips = listOf(
        VerseMetaChip("Surah ${verse.surah.number}"),
        VerseMetaChip("${verse.surah.numberOfAyahs} Verses"),
        VerseMetaChip("Juz ${verse.juz}"),
        VerseMetaChip("Page ${verse.page}")
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = true, onClick = {},
            shape = shapes.large, label = { Text(verse.surah.revelationType) },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = colorScheme.primaryContainer,
                selectedLabelColor = colorScheme.onPrimaryContainer
            )
        )
        Text(verse.surah.englishName, style = typography.titleLarge)
        Text(verse.surah.englishNameTranslation)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            metaChips.forEach {
                MetaChip(label = it.label)
            }
        }
    }
}

@Composable
fun VerseSec(modifier: Modifier = Modifier) {
    TitleAndContentSection {
        Text("")
    }
}

@Preview(showBackground = true)
@Composable
private fun TitlePrev() {
    ImanBytesTheme {
        TitleSec(
            verse = RandomVerse(
                number = 1,
                text = "The quick brown fox jumps over the lazy dog",
                surah = Surah(
                    number = "1",
                    revelationType = "Median",
                    englishName = "Al-Fatiha",
                    englishNameTranslation = "The Opening",
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RandomVerseScreenScreenPreview() {
    ImanBytesTheme(dynamicColor = false) {
        RandomVerseScreen(
            uiState = RandomVerseScreenScreenState()
        )
    }
}
