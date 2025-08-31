package com.anticbyte.imanbytes.domain

import com.anticbyte.imanbytes.data.remote.SurahDto
import com.anticbyte.imanbytes.domain.model.Surah

fun SurahDto.SurahData.toSurah(): Surah = Surah(
    number = this.number.toString(),
    name = this.name,
    englishName = this.englishName,
    englishNameTranslation = this.englishNameTranslation,
    numberOfAyahs = this.numberOfAyahs.toString(),
    revelationType = this.revelationType,
)