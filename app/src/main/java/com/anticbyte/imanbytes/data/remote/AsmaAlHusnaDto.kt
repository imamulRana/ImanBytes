package com.anticbyte.imanbytes.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class AsmaAlHusnaDto(
    val code: Int,
    val status: String,
    val data: List<Data>
) {

    @Serializable
    data class Data(
        val name: String,
        val transliteration: String,
        val number: Int,
        val en: En
    ) {

        @Serializable
        data class En(
            val meaning: String
        )
    }
}
