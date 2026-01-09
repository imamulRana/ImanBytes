package com.anticbyte.imanbytes.presentation.random_verse.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MetaChip(modifier: Modifier = Modifier, label: String) {
    FilterChip(
        selected = false, onClick = {}, {
            Text(text = label, style = typography.labelMedium)
        }, contentPadding = PaddingValues(horizontal = 2.dp), shape = shapes.large,
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = colorScheme.surface,
            selectedLabelColor = colorScheme.onSurface)
    )
}

data class VerseMetaChip(val label: String = "")