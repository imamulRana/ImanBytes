package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationType

@Composable
fun FilterRecitationType(
    selected: RecitationType = RecitationType.ARABIC,
    onSelected: (RecitationType) -> Unit
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        RecitationType.entries.forEach { recitationType ->
            val selectedType = recitationType.ordinal == selected.ordinal
            FilterChip(
                selected = selectedType,
                onClick = { onSelected(recitationType) },
                label = { Text(text = recitationType.label) },
                leadingIcon = {
                    if (selectedType) Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                        contentDescription = null
                    )
                    else null
                })
        }
    }
}