package com.anticbyte.imanbytes.utils

import android.os.Build
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.to12Hour(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val time = LocalTime.parse(this)
        val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
        time.format(formatter)
    } else {
        this
    }
}