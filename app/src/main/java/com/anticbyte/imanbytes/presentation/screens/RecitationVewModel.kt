package com.anticbyte.imanbytes.presentation.screens

import androidx.lifecycle.ViewModel
import com.anticbyte.imanbytes.domain.model.Surah
import kotlinx.coroutines.flow.Flow

interface AudioPlayerObserver {
    val currentTimeLine: Flow<Long>
    val currentPlayerState: Boolean
}

interface AudioPlayerController {
    fun playPauseToggle()
}

class AudioPlayerImpl : AudioPlayerObserver, AudioPlayerController {
    override val currentTimeLine: Flow<Long>
        get() = TODO("Not yet implemented")
    override val currentPlayerState: Boolean
        get() = TODO("Not yet implemented")

    override fun playPauseToggle() {
        TODO("Not yet implemented")
    }

}

data class RecitationUiState(
    val nowPlayingSurah: Surah = Surah(),
)

class RecitationVewModel(private val audioPlayerImpl: AudioPlayerImpl) : ViewModel(),
    AudioPlayerObserver by audioPlayerImpl, AudioPlayerController by audioPlayerImpl {
    fun doThis(){
        audioPlayerImpl.playPauseToggle()
    }
}