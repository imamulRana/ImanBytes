package com.anticbyte.imanbytes.domain.model

data class Quran(
    val verseId: Int = 0,
    val verseTitle: String = "",
    val verse: String = "",
    val verseTranslation: String = "",
    val verseInfo: String = "",
    val verseDetail: VerseDetail? = null,
) {
    data class VerseDetail(
        val markedTime: Long? = null,
        val completeTime: Long? = null,
    )
}
