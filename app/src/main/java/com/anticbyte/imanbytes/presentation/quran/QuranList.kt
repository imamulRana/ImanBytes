package com.anticbyte.imanbytes.presentation.quran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.anticbyte.imanbytes.presentation.component.FilterQuranTypes
import com.anticbyte.imanbytes.presentation.component.QuranListItem

data class RecitationVariation(
    val id: String,
    val initial: String,
    val name: String,
    val description: String,
)

@Preview
@Composable
fun QuranList(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf("Daily") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterQuranTypes(
            selected,
            onSelected = { selected = it },
            listOf("Daily", "Weekly", "Monthly")
        )
        Spacer(modifier = Modifier.height(8.dp))

        val recitationVariation = listOf(
            RecitationVariation(
                id = "ar.alafasy",
                initial = "AR",
                name = "Arabic Only",
                description = "Arabic Recitation of the whole Quran"
            ),
            RecitationVariation(
                id = "en.misharyrashidalafasyenglishtranslationsaheehibrahimwalk",
                initial = "EN",
                name = "Arabic With English Translation",
                description = "Arabic Recitation with English Translation of the whole Quran "
            ),
        )

        recitationVariation.fastForEachIndexed { index, item ->
            QuranListItem(
                leading = item.initial,
                heading = item.name,
                description = item.description
            )
        }

    }
}
