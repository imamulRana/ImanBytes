@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imaanbytes.presentation.screens.flashcard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imaanbytes.presentation.component.AppBottomBar
import com.anticbyte.imaanbytes.presentation.component.AppTopBar
import com.anticbyte.imaanbytes.presentation.component.SurahSummaryCard
import com.anticbyte.imaanbytes.presentation.component.VerseCard
import com.anticbyte.imaanbytes.presentation.theme.ImanBytesTheme

@Composable
fun FlashCardScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "Today's card"
            )
        })
    }, bottomBar = {
        AppBottomBar(selected = true) { }
    }) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(top = 28.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            pageSpacing = 16.dp
        ) { page ->
            when (page) {
                0 -> VerseCard()
                else -> SurahSummaryCard()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FlashCardScreenPreview() {
    ImanBytesTheme {
        FlashCardScreen()
    }
}