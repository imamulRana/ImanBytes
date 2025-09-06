package com.anticbyte.imanbytes.utils

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


fun main() {
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