package com.anticbyte.imanbytes.presentation.screens.textRecitation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.presentation.screens.hadith.PillarUiState
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun TxtRecitationRoute(modifier: Modifier = Modifier) {

}

@Composable
fun TxtRecitationScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    uiState: PillarUiState = PillarUiState(),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding(),
            bottom = contentPadding.calculateBottomPadding()
        ),
    ) {
        txtRecitationList(uiState.pillarData)
    }
}

fun LazyListScope.txtRecitationList(
    surahTextList: Pair<List<SurahText>, List<SurahText>>
) {
    items(minOf(surahTextList.first.size, surahTextList.second.size)) { surahText ->
        TxtRecitationItem(
            modifier = Modifier.fillMaxWidth(),
            arSurahText = surahTextList.first[surahText],
            trSurahText = surahTextList.second[surahText]
        )
        if (surahTextList.first.size - 1 != surahText)
            HorizontalDivider()
    }
}

@Composable
fun TxtRecitationItem(
    modifier: Modifier = Modifier,
    arSurahText: SurahText,
    trSurahText: SurahText
) {
    ListItem(
        modifier = modifier,
        overlineContent = {
            Row(modifier.fillMaxWidth()) {
                Text(text = arSurahText.number.toString())
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

    }
}