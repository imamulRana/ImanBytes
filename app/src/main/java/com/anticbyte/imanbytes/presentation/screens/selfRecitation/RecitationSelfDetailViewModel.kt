package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.anticbyte.imanbytes.domain.model.SelfRecitation
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.navigation.RecitationSelfDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecitationSelfDetailUiState(
    val isLoading: Boolean = false,
    val surahNumber: String = "",
    val totalVerse: String = "",
    val revelationType: String = "",
    val surahName: String = "",
    val surahEnglishTranslation: String = "",
    val surahInfo: String = "",
    val txtRecitation: List<SelfRecitation> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class RecitationSelfDetailViewModel @Inject constructor(
    private val quranRepo: QuranRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val navRoute: RecitationSelfDetailRoute = savedStateHandle.toRoute()
    private val _uiState = MutableStateFlow(RecitationSelfDetailUiState())
    val uiState = _uiState
        .onStart { loadSurahData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _uiState.value
        )

    private fun loadSurahData() {
        viewModelScope.launch {
            // Set loading ONCE at the start
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Launch both calls in parallel
            val recitationDeferred = async {
                quranRepo.getTxtSurahAndTranslation(navRoute.surahNumber)
            }
            val surahInfoDeferred = async {
                quranRepo.getSurahInfoByNumber(navRoute.surahNumber)
            }

            // Wait for both to complete
            val recitationResult = recitationDeferred.await()
            val surahInfoResult = surahInfoDeferred.await()

            // Handle results together
            var errorMessage: String? = null

            recitationResult.onSuccess { selfRecitations ->
                _uiState.update { state ->
                    selfRecitations.firstOrNull().let {
                        state.copy(
                            surahNumber = navRoute.surahNumber,
                            surahName = it?.englishName.orEmpty(),
                            revelationType = it?.revelationType.orEmpty(),
                            totalVerse = it?.numberOfAyahs.orEmpty(),
                            surahEnglishTranslation = it?.englishNameTranslation.orEmpty(),
                            txtRecitation = selfRecitations
                        )
                    }
                }
            }.onFailure { error ->
                errorMessage = error.localizedMessage
            }

            surahInfoResult.onSuccess { surahInfo ->
                _uiState.update { state ->
                    state.copy(surahInfo = surahInfo.chapterInfo.shortText)
                }
            }.onFailure { error ->
                // Only show error if recitation also failed
                _uiState.update { state ->
                    state.copy(errorMessage = errorMessage ?: error.localizedMessage)
                }
            }
            // Set loading to false ONCE at the end
            _uiState.update { state -> state.copy(isLoading = false, errorMessage = errorMessage) }
        }
    }
}