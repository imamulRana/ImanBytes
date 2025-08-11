package com.anticbyte.imanbytes.utils

sealed class ScreenUiState<out T> {
    data object Loading : ScreenUiState<Nothing>()
    data class Success<T>(val data: T) : ScreenUiState<T>()
    data class Error(val message: String) : ScreenUiState<Nothing>()
}