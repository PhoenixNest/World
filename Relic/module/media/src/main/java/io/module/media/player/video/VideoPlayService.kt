package io.module.media.player.video

import android.content.ComponentName
import androidx.activity.ComponentActivity
import androidx.lifecycle.withStarted
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import io.module.media.player.AbsPlayService
import io.module.media.player.PlayerType
import io.module.media.player.audio.AudioPlayService
import io.module.media.utils.MediaLogUtil

class VideoPlayService(
    private val activity: ComponentActivity
) : AbsPlayService() {

    private var mediaControllerFuture: ListenableFuture<MediaController>? = null

    companion object {
        private const val TAG = "VideoPlayService"
    }

    init {
        initPlayer()
        initSession()
        initMediaController()
    }

    override fun playerType(): PlayerType {
        return PlayerType.VIDEO
    }

    override fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
    }

    override fun initSession() {
        val exoPlayer = exoPlayer ?: return.also {
            MediaLogUtil.e(TAG, "Error, init the exoPlayer first.")
        }

        mediaSession = MediaSession.Builder(
            /* context = */ this,
            /* player = */ exoPlayer
        ).build()
    }

    override fun accessSessionToken(): SessionToken {
        val componentName = ComponentName(
            /* pkg = */ this,
            /* cls = */ AudioPlayService::class.java
        )

        return SessionToken(
            /* context = */ activity,
            /* serviceComponent = */ componentName
        )
    }

    private fun initMediaController() {
        val sessionToken = accessSessionToken()
        mediaControllerFuture = MediaController.Builder(
            /* context = */ this@VideoPlayService,
            /* token = */ sessionToken
        ).buildAsync()
    }

    suspend fun bindsWithPlayerView(playerView: PlayerView) {
        activity.lifecycle.withStarted {
            mediaControllerFuture?.also {
                it.addListener(
                    /* listener = */ object : Runnable {
                        override fun run() {
                            // Call controllerFuture.get() to retrieve the MediaController.
                            // MediaController implements the Player interface, so it can be
                            // attached to the PlayerView UI component.
                            playerView.player = it.get()
                        }
                    },
                    /* executor = */ MoreExecutors.directExecutor()
                )
            }
        }
    }

    override fun freeMemory() {
        super.freeMemory()
        mediaControllerFuture?.get()?.release()
        mediaControllerFuture = null
    }
}