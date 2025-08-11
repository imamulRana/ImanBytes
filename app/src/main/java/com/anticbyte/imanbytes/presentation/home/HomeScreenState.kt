package com.anticbyte.imanbytes.presentation.home

data class HomeScreenState(
    val isLoading: Boolean = false,
    val home: String = "",
    val error: String? = null
)
