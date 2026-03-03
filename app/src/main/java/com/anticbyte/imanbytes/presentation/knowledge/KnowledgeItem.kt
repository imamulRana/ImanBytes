package com.anticbyte.imanbytes.presentation.knowledge

import androidx.compose.material3.MaterialShapes
import androidx.graphics.shapes.RoundedPolygon
import com.anticbyte.imanbytes.R

enum class KnowledgeItem(
    val iconRes: Int,
    val titleRes: Int,
    val descriptionRes: Int,
    val leadingShape: RoundedPolygon
    //todo implement destinations,
) {
    ASMA(
        iconRes = R.drawable.ic_nine_nine,
        titleRes = R.string.title_asma,
        descriptionRes = R.string.desc_asma,
        leadingShape = MaterialShapes.Circle
    ),
    RAMADAN(
        iconRes = R.drawable.ic_calendar,
        titleRes = R.string.ramadan_calendar,
        descriptionRes = R.string.ramadan_calendar_desc,
        leadingShape = MaterialShapes.Circle
    ),
    SEARCH(
        iconRes = R.drawable.ic_search,
        titleRes = R.string.search_the_entire_quran,
        descriptionRes = R.string.find_what_you_want_in_the_quran,
        leadingShape = MaterialShapes.Circle
    )
}