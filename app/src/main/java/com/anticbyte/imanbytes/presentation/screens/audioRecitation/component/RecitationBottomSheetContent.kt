package com.anticbyte.imanbytes.presentation.screens.audioRecitation.component

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme


@OptIn(UnstableApi::class)
@Composable
fun RecitationBottomSheetContent(
    modifier: Modifier = Modifier,
    player: Player?,
    onReadSurahClick: (String) -> Unit,
    onDismiss: () -> Unit = {}
) {
    player?.let { audioPlayer ->
        Column(modifier = modifier.fillMaxSize()) {
            AppTopBar(
                isBackVisible = true, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ), actions = {
                    Button(
                        onClick = { onReadSurahClick("") },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(text = "Read Now")
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
                            contentDescription = null
                        )
                    }
                },
                onNavigationIconClick = onDismiss
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(200.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_genres),
                    contentDescription = null,
                    tint = colorScheme.secondaryContainer
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = audioPlayer.mediaMetadata.title.toString(),
                    style = typography.titleLargeEmphasized.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = audioPlayer.mediaMetadata.artist.toString(),
                    textAlign = TextAlign.Center
                )
                AudioControlSection(
                    modifier = Modifier,
                    player = player
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecitationSheetPreview() {
    ImanBytesTheme(dynamicColor = false, darkTheme = true) {
        RecitationBottomSheetContent(player = null, onReadSurahClick = {})
    }
}