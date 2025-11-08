package com.anticbyte.imanbytes.utils

import android.text.Html
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class SurahInfo(
    val name: String,
    val periodOfRevelation: String,
    val themeAndSubjectMatter: String
)

fun parseSurahInfo(html: String): SurahInfo {
    val doc = Jsoup.parse(html)

    val paragraph = doc.select("p + h2").map { it.text() }

    return SurahInfo(
        name = paragraph.getOrNull(0) ?: "",
        periodOfRevelation = paragraph.getOrNull(1) ?: "",
        themeAndSubjectMatter = paragraph.getOrNull(2) ?: ""
    )
}


fun main2() {
    val x = parseSurahInfo(
        html = """
            <h2>Name</h2>
            <p>The Surah derives its name from verse <a href=/39/71>71</a> and <a href=/39/73>73</a> in which the word <em>zumar</em> has occurred.</p>
            <h2>Period of Revelation</h2>
            <p>In <a href=/39/10>verse 10</a> (<em>wa ardullah-i-wasi atun</em> : and Allah's earth is vast)...</p>
            <h2>Theme and Subject matter</h2>
            <p>The entire Surah is a most eloquent and effective address...</p>
        """.trimIndent()
    )
    println(x)
}

fun main(){
    val text = "\u003Ch2\u003EName\u003C/h2\u003E\u003Cp\u003EThis Surah is named Al-Fatihah because of its subject matter. Fatihah is that which opens a subject or a book or any other thing. In other words, Al-Fatihah is a sort of preface.\u003C/p\u003E\u003Ch2\u003EPeriod of Revelation\u003C/h2\u003E\u003Cp\u003ESurah Al-Fatihah is one of the very earliest Revelations to the Holy Prophet. As a matter of fact, we learn from authentic traditions that it was the first complete Surah that was revealed to Muhammad (Allah's peace be upon him). Before this, only a few miscellaneous verses were revealed which form parts of Alaq, Muzzammil, Muddaththir, etc.\u003C/p\u003E\u003Ch2\u003ETheme\u003C/h2\u003E\u003Cp\u003EThis Surah is in fact a prayer that Allah has taught to all those who want to make a study of His book. It has been placed at the very beginning of the Quran to teach this lesson to the reader: if you sincerely want to benefit from the Quran, you should offer this prayer to the Lord of the Universe.\u003C/p\u003E\u003Cp\u003EThis preface is meant to create a strong desire in the heart of the reader to seek guidance from the Lord of the Universe Who alone can grant it. Thus Al-Fatihah indirectly teaches that the best thing for a man is to pray for guidance to the straight path, to study the Quran with the mental attitude of a seeker searching for the truth, and to recognize the fact that the Lord of the Universe is the source of all knowledge. He should, therefore, begin the study of the Quran with a prayer to Him for guidance.\u003C/p\u003E\u003Cp\u003EFrom this theme, it becomes clear that the real relation between Al-Fatihah and the Quran is not that of an introduction to a book but that of a prayer and its answer. Al-Fatihah is the prayer from the servant and the Quran is the answer from the Master to the servant's prayer. The servant prays to Allah to show him guidance and the Master places the whole of the Quran before him in answer to his prayer, as if to say, \"This is the Guidance you begged from Me.\"\u003C/p\u003E"
    val plainText = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    println(plainText.toString())
}