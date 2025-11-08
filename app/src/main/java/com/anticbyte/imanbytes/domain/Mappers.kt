package com.anticbyte.imanbytes.domain

import com.anticbyte.imanbytes.data.remote.SurahDto
import com.anticbyte.imanbytes.data.remote.SurahEditionDto
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.SurahText
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean

fun SurahDto.SurahData.toSurah(): Surah = Surah(
    number = this.number.toString(),
    name = this.name,
    englishName = this.englishName,
    englishNameTranslation = this.englishNameTranslation,
    numberOfAyahs = this.numberOfAyahs.toString(),
    revelationType = this.revelationType,
)

fun SurahEditionDto.ResponseData.Ayah.toSurahText(): SurahText = SurahText(
    number = this.number,
    text = this.text,
    numberInSurah = this.numberInSurah,
    juz = this.juz,
    manzil = this.manzil,
    page = this.page,
    ruku = this.ruku,
    hizbQuarter = this.hizbQuarter,
    sajda = this.sajda.toSajda()
)

fun SurahEditionDto.ResponseData.toSelfRecitation(): SelfRecitation = SelfRecitation(
    numberInQuran = this.number.toString(),
    name = this.name,
    englishName = this.englishName,
    englishNameTranslation = this.englishNameTranslation,
    revelationType = this.revelationType,
    numberOfAyahs = this.numberOfAyahs.toString(),
    ayahs = this.ayahs.map { it.toSelfAyah() },
    edition = this.edition.englishName
)

fun SurahEditionDto.ResponseData.Ayah.toSelfAyah(): SelfRecitation.Ayah = SelfRecitation.Ayah(
    numberInSurah = this.numberInSurah,
    text = this.text,
    sajda = this.sajda.toSajda()
)

fun JsonElement.toSajda(): Boolean {
    return when (this) {
        is JsonObject -> true
        is JsonPrimitive -> this.boolean // treat both `false` or `true` as None (or handle differently if needed)
        else -> false
    }
}