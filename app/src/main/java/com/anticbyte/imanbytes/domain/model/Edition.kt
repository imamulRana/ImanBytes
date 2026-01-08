package com.anticbyte.imanbytes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Edition(
    val identifier: String = "",
    val language: String = "",
    val name: String = "",
    val englishName: String = "",
    val format: String = "",
    val type: String = "",
    val direction: String = ""
)
