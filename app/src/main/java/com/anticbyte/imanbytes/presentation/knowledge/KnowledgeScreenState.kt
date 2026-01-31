package com.anticbyte.imanbytes.presentation.knowledge

data class KnowledgeScreenState(
    val knowledgeItems: List<KnowledgeItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
