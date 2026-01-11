package com.anticbyte.imanbytes.presentation.random_verse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.navigation.RandomVerseRoute
import dagger.hilt.android.lifecycle.HiltViewModel
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


class RandomVerseViewModel @Inject constructor(
    private val quranRepo: QuranRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val args = savedStateHandle.toRoute<RandomVerseRoute>()

    private val _uiState = MutableStateFlow(RandomVerseScreenScreenState())
    val uiState: StateFlow<RandomVerseScreenScreenState> = _uiState.asStateFlow()
        .onStart {
            fetchVerseAndTafsir()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _uiState.value
        )


    fun fetchVerseAndTafsir() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                val verse = quranRepo.getRandomVerse(args.verseId).getOrThrow()
                val tafsir = quranRepo.getTafsir(
                    verse.surah.number,
                    verse.numberInSurah.toString()
                ).getOrThrow()
                verse to tafsir
            }.onSuccess { (verse, tafsir) ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        verse = verse,
                        tafsir = tafsir
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.localizedMessage
                    )
                }
            }
        }
    }
}
