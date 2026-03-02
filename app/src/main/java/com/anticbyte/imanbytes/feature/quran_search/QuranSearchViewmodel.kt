package com.anticbyte.imanbytes.feature.quran_search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranSearchViewmodel @Inject constructor(
    private val quranRepo: QuranRepo
) : ViewModel() {
    val uiState: StateFlow<QuranSearchUiState>
        field = MutableStateFlow(QuranSearchUiState())
    val searchFieldState = TextFieldState()

    /**
     * Search quran by query and update the uiState
     * @param query String
     * @return Unit
     */
    fun searchQuran(query: String) {
        viewModelScope.launch {
            quranRepo.searchQuran(query).onSuccess { result ->
                uiState.value =
                    uiState.value.copy(isLoading = false, searchItems = result, errorMessage = null)
                searchFieldState.setTextAndPlaceCursorAtEnd(
                    result.query
                )
            }.onFailure {
                searchFieldState.setTextAndPlaceCursorAtEnd(query)
                uiState.value =
                    uiState.value.copy(isLoading = false, errorMessage = it.localizedMessage)
            }
        }
    }
}