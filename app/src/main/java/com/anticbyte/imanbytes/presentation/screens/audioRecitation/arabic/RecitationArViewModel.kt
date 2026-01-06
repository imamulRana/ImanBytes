package com.anticbyte.imanbytes.presentation.screens.audioRecitation.arabic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecitationArViewModel
@Inject constructor(
    private val quranRepo: QuranRepo,
    @ApplicationContext private val context: Context,
    private val sessionToken: SessionToken
) : ViewModel() {
    private var controllerFuture: ListenableFuture<MediaController>? = null

    private val _mediaPlayer = MutableStateFlow<Player?>(null)
    val mediaPlayer = _mediaPlayer.asStateFlow()
    private val _recitationUiState = MutableStateFlow(RecitationArScreenState(isLoading = true))
    val recitationUiState: StateFlow<RecitationArScreenState> = _recitationUiState
        .onStart {
            fetchAllSurah()
            initializeController()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _recitationUiState.value
        )

    private fun initializeController() {
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture?.addListener({
            try {
                val controller = controllerFuture?.get()
                _mediaPlayer.value = controller
                controller?.setMediaItem(MediaItem.fromUri("https://cdn.islamic.network/quran/audio/128/ar.alafasy/4125.mp3"))
                controller?.prepare()
                // Optional: Prepare default playlist if empty
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, MoreExecutors.directExecutor())
    }

    fun fetchAllSurah() {
        viewModelScope.launch {
            val response = quranRepo.getAllSurah()
            response.fold(
                onSuccess =
                    { surahs ->
                        _recitationUiState.update { state ->
                            state.copy(
                                surahList = surahs, isLoading = false
                            )
                        }
                    },
                onFailure = {
                    _recitationUiState.update { state ->
                        state.copy(errorMessages = it.localizedMessage)
                    }
                })
        }
    }
}
