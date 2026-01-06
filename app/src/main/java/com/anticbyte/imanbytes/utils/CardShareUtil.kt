package com.anticbyte.imanbytes.utils

import android.content.Context
import android.content.Intent

fun Context.shareCardText(
    text: String
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

    val chooserIntent = Intent.createChooser(intent, "Share").apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    startActivity(chooserIntent)
}