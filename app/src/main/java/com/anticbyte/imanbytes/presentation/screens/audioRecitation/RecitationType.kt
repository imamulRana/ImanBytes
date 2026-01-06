package com.anticbyte.imanbytes.presentation.screens.audioRecitation

enum class RecitationType(
    val label: String,
    val recitationId: String
) {
    ARABIC(
        label = "Arabic",
        recitationId = "ar.alafasy"
    ),
    TRANSLATION(
        label = "Arabic & Translate",
        recitationId = "en.misharyrashidalafasyenglishtranslationsaheehibrahimwalk"
    )
}