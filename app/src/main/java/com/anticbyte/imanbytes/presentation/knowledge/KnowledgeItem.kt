package com.anticbyte.imanbytes.presentation.knowledge

import com.anticbyte.imanbytes.R

enum class KnowledgeItem(
    val iconRes: Int,
    val titleRes: Int,
    val descriptionRes: Int,
    //todo implement destinations
) {
    QURAN(
        iconRes = R.drawable.ic_book_fill_sharp,
        titleRes = R.string.title_quran,
        descriptionRes = R.string.desc_quran,
    ),
    HADITH(
        iconRes = R.drawable.ic_book_fill_sharp,
        titleRes = R.string.title_hadith,
        descriptionRes = R.string.desc_hadith,
    ),
    PILLARS(
        iconRes = R.drawable.ic_book_fill_sharp,
        titleRes = R.string.title_pillars,
        descriptionRes = R.string.desc_pillars,
    )
}