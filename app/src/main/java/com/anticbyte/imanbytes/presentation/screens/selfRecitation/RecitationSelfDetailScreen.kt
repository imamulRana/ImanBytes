package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.customInnerPadding
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.paddingWithoutTop
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationSelfDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationSelfDetailViewModel,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RecitationSelfDetailScreen(
        modifier = modifier, uiState = uiState, onNavigateBack = navigateBack
    )
}

@Composable
fun RecitationSelfDetailScreen(
    modifier: Modifier = Modifier,
    uiState: RecitationSelfDetailUiState = RecitationSelfDetailUiState(),
    onNavigateBack: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            AppTopBar(
                title = uiState.surahName,
                subtitle = uiState.surahEnglishTranslation,
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.play_arrow_24px),
                            contentDescription = null
                        )
                    }
                })
        },
        contentWindowInsets = WindowInsets(bottom = 88.dp)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding.customInnerPadding(),
        ) {
            if (uiState.isLoading) loadingItem()
            else {
                txtRecitationItemDesc2(
                    revelationType = uiState.revelationType,
                    totalVerse = uiState.totalVerse,
                    surahInfo = uiState.surahInfo
                )
                recitationItemsSelfDetail(uiState.txtRecitation)
            }
        }
    }
}

fun LazyListScope.recitationItemsSelfDetail(surahTextList: List<SelfRecitation>) {
    if (surahTextList.isNotEmpty()) items(surahTextList.first().ayahs.size) { surahText ->
        RecitationSelfDetailListItem(
            modifier = Modifier.fillMaxWidth(),
            arSurahText = surahTextList.first().ayahs[surahText],
            trSurahText = surahTextList.last().ayahs[surahText],
            surahNumber = surahTextList.first().numberInQuran
        )
        if (surahTextList.first().ayahs.size - 1 != surahText) HorizontalDivider()
    }
}

fun LazyListScope.txtRecitationItemDesc(@StringRes descriptionRes: Int) {
    item {
        Text(
            text = stringResource(descriptionRes),
            textAlign = TextAlign.Justify,
            style = typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

fun LazyListScope.txtRecitationItemDesc2(
    revelationType: String, totalVerse: String, surahInfo: String
) {
    item {
        Column(modifier = Modifier.paddingWithoutTop(16.dp)) {
            Text(buildAnnotatedString {
                withStyle(
                    style = typography.labelSmall.toSpanStyle()
                        .copy(color = colorScheme.onBackground.copy(.5f))
                ) {
                    append("revelation type".uppercase())
                    append("\n")
                }
                append(revelationType)
                append("\n")
                withStyle(
                    style = typography.labelSmall.toSpanStyle()
                        .copy(color = colorScheme.onBackground.copy(.5f))
                ) {
                    append("total verse".uppercase())
                    append("\n")
                }
                append(totalVerse)
                append("\n\n")
                /*withStyle(
                    style = typography.labelSmall.toSpanStyle()
                        .copy(color = colorScheme.onBackground.copy(.5f))
                ) {
                    append("surah info".uppercase())
                    append("\n")
                }*/
                append(surahInfo)
            }, textAlign = TextAlign.Justify)
        }
    }
}

@Composable
fun RecitationSelfDetailListItem(
    modifier: Modifier = Modifier,
    surahNumber: String,
    arSurahText: SelfRecitation.Ayah,
    trSurahText: SelfRecitation.Ayah
) {
    ListItem(modifier = modifier, overlineContent = {
        Row(modifier.fillMaxWidth()) {
            Text(text = surahNumber)
            Text(":${arSurahText.numberInSurah}")
            Spacer(Modifier.weight(1f))
            if (arSurahText.sajda) {
                Text(
                    text = stringResource(R.string.sajda),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }, headlineContent = {
        Text(
            modifier = modifier,
            text = arSurahText.text,
            textAlign = TextAlign.Right,
            style = typography.headlineLarge.copy(
                fontFamily = FontFamily(Font(R.font.lateef))
            )
        )
    }, supportingContent = {
        Column {
            Text(
                text = trSurahText.text,
                textAlign = TextAlign.Justify,
            )
        }
    })
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme() {
        RecitationSelfDetailScreen()
    }
}