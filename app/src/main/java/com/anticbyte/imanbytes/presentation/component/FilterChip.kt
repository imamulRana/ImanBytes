package com.anticbyte.imanbytes.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imanbytes.R

@Composable
fun ChooseTopicsChip(
    modifier: Modifier = Modifier,
    @StringRes topics: List<Int>,
    selectedTopic: Int = 0,
    onTopicSelected: (Int) -> Unit,
    enabled: Boolean
) {
    topics.fastForEachIndexed { index, topic ->
        FilterChip(
            selected = selectedTopic == index,
            onClick = { onTopicSelected(index) },
            label = { Text(text = stringResource(id = topic)) },
            modifier = modifier.size(110.dp, 60.dp),
            shape = CircleShape,
            leadingIcon = {
                if (selectedTopic == index) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                        contentDescription = ""
                    )
                }
            },
//            enabled = index == 0
            enabled = index == 0
        )
    }
}

@Composable
fun FilterQuranTypes(
    selected: String = "",
    onSelected: (String) -> Unit,
    label: List<String>
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), maxItemsInEachRow = 3) {
        label.fastForEachIndexed { index, lab ->
            FilterChip(
                selected = selected == lab,
                onClick = { onSelected(lab) },
                label = { Text(text = lab) })
        }
    }
}