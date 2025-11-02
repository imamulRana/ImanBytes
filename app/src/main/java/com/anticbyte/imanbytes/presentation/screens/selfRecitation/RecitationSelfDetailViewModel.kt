package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.navigation.RecitationSelfDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecitationSelfDetailUiState(
    val isLoading: Boolean = false,
    val surahNumber: String = "",
    val txtRecitation: Pair<List<SurahText>, List<SurahText>> = Pair(listOf(), listOf()),
    val errorMessage: String? = null
)

@HiltViewModel
class RecitationSelfDetailViewModel @Inject constructor(
    private val quranRepo: QuranRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val navRoute: RecitationSelfDetailRoute = savedStateHandle.toRoute()
    private val _uiState = MutableStateFlow(RecitationSelfDetailUiState(isLoading = true))
    val uiState = _uiState
        .onStart { fetchTxtRecitation(surahNumber = navRoute.surahNumber) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _uiState.value
        )

    fun fetchTxtRecitation(surahNumber: String) {
        viewModelScope.launch {
            val data = quranRepo.getTxtSurahAndTranslation(surahNumber)
            _uiState.value = _uiState.value.copy(
                surahNumber = surahNumber,
                isLoading = false,
                txtRecitation = data.getOrNull() ?: Pair(listOf(), listOf())
            )
        }
    }
}