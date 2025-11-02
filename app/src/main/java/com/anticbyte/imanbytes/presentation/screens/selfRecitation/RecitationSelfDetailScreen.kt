package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.SurahText
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
        modifier = modifier,
        uiState = uiState,
        onNavigateBack = navigateBack
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
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Recite Quran",
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
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding.customInnerPadding(),
        ) {
            if (uiState.isLoading) loadingItem()
            else {
                txtRecitationItemDesc(descriptionRes = R.string.recitation_description_arabic)
                recitationItemsSelfDetail(surahNumber = uiState.surahNumber,uiState.txtRecitation,)
            }
        }
    }
}

fun LazyListScope.recitationItemsSelfDetail(
    surahNumber: String,
    surahTextList: Pair<List<SurahText>, List<SurahText>>
) {
    items(surahTextList.first.size) { surahText ->
        RecitationSelfDetailListItem(
            modifier = Modifier.fillMaxWidth(),
            arSurahText = surahTextList.first[surahText],
            trSurahText = surahTextList.second[surahText],
            surahNumber = surahNumber
        )
        if (surahTextList.first.size - 1 != surahText)
            HorizontalDivider()
    }
}

fun LazyListScope.txtRecitationItemDesc(@StringRes descriptionRes: Int) {
    item {
        Text(
            text = stringResource(descriptionRes),
            textAlign = TextAlign.Justify,
            modifier = Modifier.paddingWithoutTop(16.dp)
        )
    }
}

@Composable
fun RecitationSelfDetailListItem(
    modifier: Modifier = Modifier,
    surahNumber: String,
    arSurahText: SurahText,
    trSurahText: SurahText
) {
    ListItem(
        modifier = modifier,
        overlineContent = {
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
                text = arSurahText.text.trim('۞'),
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
    ImanBytesTheme {
        RecitationSelfDetailScreen()
    }
}