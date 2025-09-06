
package com.anticbyte.imanbytes.presentation.topics

import android.window.BackEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.ChooseTopicsChip
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicsScreen(
    modifier: Modifier = Modifier,
    navigateToFlashCard: () -> Unit = {},
    onBackEvent: @Composable (BackEvent) -> Unit = {}
) {
    var selectedTopic by rememberSaveable { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(topBar = {
        AppTopBar(
            title = "Choose Topics",
        )
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ChooseTopicsChip(
                    topics = listOf(
                        R.string.topic_all,
                        R.string.title_quran,
                        R.string.title_hadith,
                        R.string.title_seerah,
                        R.string.title_dua
                    ),
                    selectedTopic = selectedTopic,
                    onTopicSelected = { selectedTopic = it },
                    enabled = true
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = navigateToFlashCard,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview
@Composable
private fun TopicsScreenPreview() {
    ImanBytesTheme {
        TopicsScreen()
    }
}