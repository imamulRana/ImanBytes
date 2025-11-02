package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah


@Composable
fun RecitationBottomSheetContent(
    modifier: Modifier = Modifier,
    playBackState: RecitationPlayBackState,
    actions: RecitationPlaybackAction,
    nowPlayingSurah: Surah? = null,
    onReadSurahClick: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Button(
                onClick = { onReadSurahClick(nowPlayingSurah?.englishName.orEmpty()) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.read_surah))
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward), null)
            }
            Text(text = nowPlayingSurah?.englishName.orEmpty(), style = typography.displayMedium)
            Text(
                text = nowPlayingSurah?.englishNameTranslation.orEmpty(),
                style = typography.bodyLarge
            )
        }
        RecitationPlayBack(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            playBackState = playBackState,
            actions = actions
        )
    }
}