package com.anticbyte.imanbytes.presentation.screens.hadith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.model.SurahText
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PillarUiState(
    val pillarData: Pair<List<SurahText>, List<SurahText>> = Pair(listOf(), listOf()),
)

@HiltViewModel
class PillarViewModel @Inject constructor(
    private val quranRepo: QuranRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(PillarUiState())
    val uiState: StateFlow<PillarUiState> = _uiState
        .onStart { getPillarData() }
        .stateIn(
            viewModelScope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            _uiState.value
        )

    fun getPillarData() {
        viewModelScope.launch {
            val data = quranRepo.readSurahWithTranslation("13")
            _uiState.value = _uiState.value.copy(pillarData = data.getOrThrow() )
        }
    }
}