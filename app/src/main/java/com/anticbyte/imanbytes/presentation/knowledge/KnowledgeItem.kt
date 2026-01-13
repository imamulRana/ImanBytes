package com.anticbyte.imanbytes.presentation.knowledge

import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.ui.graphics.Shape
import androidx.graphics.shapes.RoundedPolygon
import com.anticbyte.imanbytes.R

enum class KnowledgeItem(
    val iconRes: Int,
    val titleRes: Int,
    val descriptionRes: Int,
    val leadingShape: RoundedPolygon
    //todo implement destinations,
) {
    QURAN(
        iconRes = R.drawable.ic_book_fill_sharp,
        titleRes = R.string.title_quran,
        descriptionRes = R.string.desc_quran,
        leadingShape = MaterialShapes.Pill
    ),
    /*HADITH(
        iconRes = R.drawable.ic_book_fill_sharp,
        titleRes = R.string.title_hadith,
        descriptionRes = R.string.desc_hadith,
    ),
    PILLARS(
        iconRes = R.drawable.ic_book_fill_sharp,
        titleRes = R.string.title_pillars,
        descriptionRes = R.string.desc_pillars,
    ),*/
    ASMA(
        iconRes = R.drawable.ic_nine_nine,
        titleRes = R.string.title_asma,
        descriptionRes = R.string.desc_asma,
        leadingShape = MaterialShapes.Clover4Leaf
    ),
    RAMADAN(
        iconRes = R.drawable.ic_duhr,
        titleRes = R.string.ramadan_calendar,
        descriptionRes = R.string.ramadan_calendar_desc,
        leadingShape = MaterialShapes.ClamShell
    )
}