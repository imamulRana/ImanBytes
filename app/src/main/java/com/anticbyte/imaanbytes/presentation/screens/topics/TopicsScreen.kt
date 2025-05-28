@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

package com.anticbyte.imaanbytes.presentation.screens.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imaanbytes.presentation.component.AppTopBar
import com.anticbyte.imaanbytes.presentation.component.ChooseTopicsChip
import com.anticbyte.imaanbytes.presentation.theme.ImanBytesTheme

@Composable
fun TopicsScreen(modifier: Modifier = Modifier) {
    var selectedTopic by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(topBar = {
        AppTopBar(
            title = "Choose Topics",
            subtitle = "Select topics to get your desired knowledge",
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
                        "All", "Quran", "Seerah", "Dua"
                    ),
                    selectedTopic = selectedTopic,
                    onTopicSelected = { selectedTopic = it },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = {},
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = ButtonDefaults.MediumContentPadding
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