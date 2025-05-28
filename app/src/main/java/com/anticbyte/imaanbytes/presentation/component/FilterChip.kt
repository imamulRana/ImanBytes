package com.anticbyte.imaanbytes.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun ChooseTopicsChip(
    modifier: Modifier = Modifier,
    topics: List<String>,
    selectedTopic: Int = 0,
    onTopicSelected: (Int) -> Unit
) {
    topics.fastForEachIndexed { index, topic ->
        FilterChip(
            selected = selectedTopic == index,
            onClick = { onTopicSelected(index) },
            label = { Text(text = topic) },
            modifier = modifier
                .height(60.dp)
                .width(110.dp),
            shape = CircleShape
        )
    }
}