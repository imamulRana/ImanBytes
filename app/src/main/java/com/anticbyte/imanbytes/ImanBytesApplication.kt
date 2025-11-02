package com.anticbyte.imanbytes

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import androidx.media3.session.SessionToken
import com.anticbyte.imanbytes.feature.AudioPlaybackService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImanBytesApplication : Application() {
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        val sessionToken = SessionToken(this, ComponentName(this, AudioPlaybackService::class.java))
    }
}