package com.anticbyte.imanbytes.presentation.screens.recitation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationType

fun LazyListScope.recitationSectionHeader(
    modifier: Modifier = Modifier,
    selected: RecitationType = RecitationType.ARABIC,
    onSelected: (RecitationType) -> Unit

) {
    item {
        Column(
            modifier = modifier.paddingWithoutTop(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.recitation_description),
                textAlign = TextAlign.Justify,
                style = typography.bodyLarge
            )
            FilterRecitationType(selected = selected, onSelected = onSelected)
        }
    }
}