package com.anticbyte.imanbytes

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImanBytesApplication : Application() {
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }
}