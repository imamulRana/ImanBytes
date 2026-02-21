package com.anticbyte.imanbytes.presentation.home.utils

import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.RandomVerse

fun RandomVerse.shareVerse() = """
"${this.text}"

— Surah ${this.surah.englishName}
Ayah ${this.numberInSurah}

May this verse be a reminder for the heart 🤍
""".trimIndent()

fun Asma.shareAsma() = """
${this.number}. ${this.name}
(${this.transliteration})
                            
Meaning: ${this.englishMeaning}
                            
Learn and remember the beautiful names of Allah 🤍
""".trimIndent()