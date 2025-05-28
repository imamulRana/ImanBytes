package com.anticbyte.imaanbytes.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.anticbyte.imaanbytes.R
import com.anticbyte.imaanbytes.presentation.theme.ImanBytesTheme

@Composable
fun VerseCard(modifier: Modifier = Modifier) {
    Card(
        onClick = {}, modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "اِقْرَاْ",
                    style = typography.displayLarge,
                    textAlign = TextAlign.Center
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        modifier = Modifier,
                        text = "Read",
                        style = typography.displaySmall,
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 8.dp),
                        text = "(Noun)",
                        style = typography.bodySmall,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "وَمَا قَدَرُوا اللّٰهَ حَقَّ قَدْرِهٖ ۖۗ وَالْاَرْضُ جَمِیْعًا قَبْضَتُهٗ یَوْمَ الْقِیٰمَةِ وَالسَّمٰوٰتُ مَطْوِیّٰتٌ بِیَمِیْنِهٖ ؕ سُبْحٰنَهٗ وَتَعٰلٰی عَمَّا یُشْرِكُوْنَ ۟۟۠",
                fontFamily = FontFamily(
                    Font(R.font.noto_sans_arabic)
                )
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "They have not appraised Allāh with true appraisal, while the earth entirely will be [within] His grip on the Day of Resurrection, and the heavens will be folded in His right hand. Exalted is He and high above what they associate with Him.",
                textAlign = TextAlign.Justify,
                style = typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Text("- Saheeh International", modifier = Modifier.padding(bottom = 8.dp).align(Alignment.End))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                SuggestionChip(onClick = {}, label = {

                    Text(modifier = Modifier, text = "Surah Al-'Alaq")
                })
                FilledIconButton(
                    onClick = {}, modifier = Modifier
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.arrow_forward),
                        contentDescription = "Localized description"
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SurahSummaryCard(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ) {
        Text(
            text = LoremIpsum(220).values.iterator().next(),
            modifier = Modifier.padding(bottom = 28.dp),
            textAlign = TextAlign.Justify,
            style = typography.bodyLarge
        )

    }
}

@Preview
@Composable
private fun FlashCardPreview() {
    ImanBytesTheme {
        VerseCard()
    }

}

@Preview
@Composable
private fun SurahSummaryCardPreview() {
    SurahSummaryCard()
}