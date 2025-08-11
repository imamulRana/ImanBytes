package com.anticbyte.imanbytes.presentation.knowledge

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class KnowledgeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(KnowledgeScreenState())
    val uiState = _uiState.asStateFlow()
}