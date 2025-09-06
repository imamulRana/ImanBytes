@file:OptIn(ExperimentalMaterial3Api::class)

package com.anticbyte.imanbytes.presentation.quran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.QuranScreenItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun QuranScreen(modifier: Modifier = Modifier) {
    Scaffold(topBar = { AppTopBar(
        title = "Quran"
    ) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuranScreenItem(
                titleRes = R.string.title_arabic_recitation,
                descriptionRes = R.string.desc_arabic_recitation,
                styleRes = R.string.arabic_recitation_spl,
                onItemClick = {}
            )
            QuranScreenItem(
                titleRes = R.string.title_translation_recitation,
                descriptionRes = R.string.desc_translation_recitation,
                styleRes = R.string.translation_recitation_spl,
                onItemClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        QuranScreen()
    }
}