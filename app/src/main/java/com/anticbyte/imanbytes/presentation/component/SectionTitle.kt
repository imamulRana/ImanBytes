package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Section title for composable that are in a section
 *
 * @param sectionTitle title of the section
 * @param modifier modifier for the composable
 */
@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    sectionTitle: String
) {
    Text(
        modifier = modifier
            .padding(start = 20.dp, end = 16.dp, bottom = 8.dp),
        text = sectionTitle.uppercase(),
        style = typography.labelLarge,
        color = colorScheme.onBackground.copy(alpha = 0.6f)
    )
}