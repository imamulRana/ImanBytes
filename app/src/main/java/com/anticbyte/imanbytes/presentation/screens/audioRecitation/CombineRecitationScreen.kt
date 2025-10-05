import androidx.annotation.OptIn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import androidx.media3.ui.compose.state.rememberPlaybackSpeedState
import androidx.media3.ui.compose.state.rememberPresentationState
import androidx.media3.ui.compose.state.rememberSeekBackButtonState
import androidx.media3.ui.compose.state.rememberSeekForwardButtonState

@OptIn(UnstableApi::class)
@Composable
fun Media3Ui(
    modifier: Modifier = Modifier,
    player: Player
) {
    val state = rememberPlayPauseButtonState(player)
    val x = rememberSeekForwardButtonState(player)
    val y = rememberPresentationState(player)

    PlayerSurface(player = player)
    Button(onClick = {state.showPlay}) {
        state

        x.seekForwardAmountMs
    }
}

@Preview
@Composable
private fun DefPrev() {
    val context = LocalContext.current
    Media3Ui(player = ExoPlayer.Builder(context).build())
}
