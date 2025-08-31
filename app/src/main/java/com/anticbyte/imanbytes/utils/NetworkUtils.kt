package com.anticbyte.imanbytes.utils

import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.LoggingConfig
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup

val jsonConfig: ContentNegotiationConfig.() -> Unit = {
    json(
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    )
}

val loggingConfig: LoggingConfig.() -> Unit = {
    logger = Logger.ANDROID
    level = LogLevel.ALL
}

val defaultRequestConfig: DefaultRequest.DefaultRequestBuilder.() -> Unit = {
    url(urlString = "https://anticbyte.com/api/v2")
}

inline fun <T> apiSafeCall(block: () -> T): Result<T> {
    return runCatching { block() }.onFailure {
        it.localizedMessage
    }
}