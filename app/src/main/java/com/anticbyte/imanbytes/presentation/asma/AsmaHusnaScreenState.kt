package com.anticbyte.imanbytes.presentation.asma

import com.anticbyte.imanbytes.domain.model.Asma

data class AsmaHusnaScreenState(
    val asmaList: List<Asma> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)