package com.anticbyte.imanbytes.feature

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

//@UnstableApi
//@AndroidEntryPoint
/*
class QuranMediaService : MediaSessionService() {

    @Inject
    lateinit var quranAudioManager: QuranAudioManager
    private lateinit var mediaSession: MediaSession
    private lateinit var defaultNotificationProvider: MediaNotification.Provider

    // custom command action strings
    companion object {
        const val ACTION_SEEK_FORWARD_10 = "com.yourapp.SEEK_FORWARD_10"
        const val ACTION_SEEK_BACK_10 = "com.yourapp.SEEK_BACK_10"
        const val NOTIFICATION_CHANNEL_ID = "quran_audio_channel"
        const val NOTIFICATION_ID = 1001
    }

    override fun onCreate() {
        super.onCreate()

        // 1) Build a DefaultMediaNotificationProvider, set small icon + channel
        defaultNotificationProvider = DefaultMediaNotificationProvider.Builder(this)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setNotificationId(NOTIFICATION_ID)
            .build()

        // 2) Build MediaSession and register notification provider
        mediaSession = MediaSession.Builder(this, quranAudioManager.exoPlayer)
            .setId("QuranMediaSession")
            .setSessionActivity(
                PendingIntent.getActivity(
                    this, 0, packageManager.getLaunchIntentForPackage(packageName),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            // register the notification provider so Media3 uses it for the notification
            .setCallback(object : MediaSession.Callback {
                // Advertise allowed commands and custom session commands in onConnect()
                override fun onConnect(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo
                ): MediaSession.ConnectionResult {
                    // Player commands: play/pause and seek-to support (required to enable seekbar).
                    val playerCommands = Player.Commands.Builder()
                        .add(Player.COMMAND_PLAY_PAUSE)
                        // important: enables draggable seek bar
                        .build()

                    // Custom session commands for forward/back 10s
                    val customForward = SessionCommand(ACTION_SEEK_FORWARD_10, Bundle())
                    val customBack = SessionCommand(ACTION_SEEK_BACK_10, Bundle())

                    val sessionCommands = SessionCommands.Builder()
                        .add(customForward)
                        .add(customBack)
                        .build()

                    return MediaSession.ConnectionResult.accept(sessionCommands, playerCommands)
                }

                // Handle custom commands (notification buttons will send custom commands)
                override fun onCustomCommand(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                    customCommand: SessionCommand,
                    args: Bundle
                ): ListenableFuture<SessionResult> {
                    val executor = MoreExecutors.directExecutor()
                    val result = SettableFuture.create<SessionResult>()

                    when (customCommand.customAction) {
                        ACTION_SEEK_FORWARD_10 -> {
                            quranAudioManager.seekAudio(PlayerSeekType.FORWARD, 10_000L)
                            result.set(SessionResult(SessionResult.RESULT_SUCCESS))
                        }

                        ACTION_SEEK_BACK_10 -> {
                            quranAudioManager.seekAudio(PlayerSeekType.BACKWARD, 10_000L)
                            result.set(SessionResult(SessionResult.RESULT_SUCCESS))
                        }

                        else -> result.set(SessionResult(SessionError.ERROR_NOT_SUPPORTED))
                    }
                    return result
                }

                // Optionally respond to player command requests (you can filter if needed)
                override fun onPlayerCommandRequest(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                    playerCommand: Int
                ): Int {
                    // by default defer to super (which delegates to player)
                    return super.onPlayerCommandRequest(session, controller, playerCommand)
                }
            })
            .build()

        // set the session token for MediaSessionService
        // ensure notification channel exists
        createNotificationChannel()

        // Note: MediaSession was constructed with the player, so Media3 will route transport commands to the player.
        // We don't need a separate notification manager; Media3 posts a MediaNotification for us via the provider.
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(androidx.media3.session.R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description =
                    getString(androidx.media3.session.R.string.default_notification_channel_name)
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        mediaSession.release()
        quranAudioManager.releasePlayer()
        super.onDestroy()
    }

    // Minimal browser root (if needed). MediaSessionService requires this override (optional if not using MediaBrowser)
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession
}
*/


class QuranAudioService : MediaSessionService() {
    private lateinit var exoPlayer: ExoPlayer
    private var mediaSession: MediaSession? = null
    override fun onCreate() {
        super.onCreate()
        exoPlayer = ExoPlayer.Builder(this)
            .build()


    }

    override fun onDestroy() {
        mediaSession?.release()
        mediaSession = null
        if (::exoPlayer.isInitialized)
            exoPlayer.release()

        super.onDestroy()
    }

    @OptIn(UnstableApi::class)
    override fun onTaskRemoved(rootIntent: Intent?) {
        pauseAllPlayersAndStopSelf()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

}