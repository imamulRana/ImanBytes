package com.anticbyte.imanbytes.domain

import com.anticbyte.imanbytes.data.remote.AsmaAlHusnaDto
import com.anticbyte.imanbytes.data.remote.GetPrayerTimesByMonthDto
import com.anticbyte.imanbytes.data.remote.GetRandomVerseDto
import com.anticbyte.imanbytes.data.remote.GetTafsirDto
import com.anticbyte.imanbytes.data.remote.PrayerTimesResDto
import com.anticbyte.imanbytes.data.remote.SurahDto
import com.anticbyte.imanbytes.data.remote.SurahEditionDto
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.domain.model.Edition
import com.anticbyte.imanbytes.domain.model.RamadanCalender
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.domain.model.Tafsir
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

fun PrayerTimesResDto.Timings.toPrayerTime(): List<Pair<String, String>> = listOf(
    "Fajr" to fajr,
    "Dhuhr" to dhuhr,
    "Asr" to asr,
    "Maghrib" to maghrib,
    "Isha" to isha
)

fun AsmaAlHusnaDto.Data.toAsma(): Asma = Asma(
    name = this.name,
    transliteration = this.transliteration,
    englishMeaning = this.en.meaning,
    number = this.number
)

fun GetRandomVerseDto.toDomain(): RandomVerse {
    return RandomVerse(
        number = data.number,
        text = data.text,
        // Mapping Edition (Assuming Edition domain model matches DTO structure)
        edition = Edition(
            identifier = data.edition.identifier,
            language = data.edition.language,
            name = data.edition.name,
            englishName = data.edition.englishName,
            format = data.edition.format,
            type = data.edition.type,
            direction = data.edition.direction
        ),
        // Mapping Surah with Int to String conversion
        surah = Surah(
            number = data.surah.number.toString(),
            name = data.surah.name,
            englishName = data.surah.englishName,
            englishNameTranslation = data.surah.englishNameTranslation,
            numberOfAyahs = data.surah.numberOfAyahs.toString(),
            revelationType = data.surah.revelationType
        ),
        numberInSurah = data.numberInSurah,
        juz = data.juz,
        manzil = data.manzil,
        page = data.page,
        ruku = data.ruku,
        hizbQuarter = data.hizbQuarter,
        sajda = data.sajda
    )
}

fun GetTafsirDto.toDomain(): Tafsir {
    return Tafsir(
        surahName = surahName,
        surahNo = surahNo,
        ayahNo = ayahNo,
        tafsirs = tafsirs.map { it.toDomain() }
    )
}

fun GetTafsirDto.Tafsir.toDomain(): Tafsir.TafsirData {
    return Tafsir.TafsirData(
        author = author,
        groupVerse = groupVerse,
        content = content
    )
}

fun GetPrayerTimesByMonthDto.toRamadanCalendar(): List<RamadanCalender> {
    return data.map { it.toRamadanCalendar() }
}

private fun GetPrayerTimesByMonthDto.Data.toRamadanCalendar(): RamadanCalender {
    return RamadanCalender(
        //new
        gregorianDate = date.readable,
        hijriDate = date.hijri.day.plus(" " + date.hijri.month.en).plus(" " + date.hijri.year),
        holidays = date.hijri.holidays,
        prayerTimes = timings.toPrayerTimePairs()
    )
}

private fun GetPrayerTimesByMonthDto.Data.Timings.toPrayerTimePairs(): List<Pair<String, String>> {
    fun String.cleanTime() = substringBefore(" ")

    return listOf(
        "Imsak" to imsak.cleanTime(),
        "Fajr" to fajr.cleanTime(),
        "Sunrise" to sunrise.cleanTime(),
        "Dhuhr" to dhuhr.cleanTime(),
        "Asr" to asr.cleanTime(),
        "Maghrib" to maghrib.cleanTime(),
        "Sunset" to sunset.cleanTime(),
        "Isha" to isha.cleanTime(),
        "Midnight" to midnight.cleanTime(),
        "Firstthird" to firstThird.cleanTime(),
        "Lastthird" to lastThird.cleanTime()
    )
}