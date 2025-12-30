package com.anticbyte.imanbytes.presentation.screens.hadith

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.domain.model.SurahText
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jsoup.Jsoup

@Composable
fun HadithScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Coming Soon!")
    }
}

@Composable
fun PillarList(
    modifier: Modifier = Modifier,
    surahText: SurahText,
    translationList: String
) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            "${surahText.text} ${surahText.numberInSurah}",
            modifier,
            style = typography.titleLarge,
            textAlign = TextAlign.Right
        )

        Text(translationList, modifier, style = typography.bodyMedium, textAlign = TextAlign.Left)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefPrev(modifier: Modifier = Modifier) {
    val response by produceState("") {
        val client = HttpClient(Android)
        val json = client.get("https://api.quran.com/api/v4/chapters/1/info").bodyAsText()
        client.close()

        val jsonObj = Json.parseToJsonElement(json).jsonObject
        val chapterInfo = jsonObj["chapter_info"]?.jsonObject
        val shortText = chapterInfo?.get("short_text")?.jsonPrimitive?.content ?: ""
        value = shortText
    }

    Text(text = response)
}