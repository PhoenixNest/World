package io.module.media.player

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken

/**
 * Reference docs:
 *
 * - [Audio and video overview](https://developer.android.google.cn/media/audio-and-video)
 * - [Create a basic media player app using Media3 ExoPlayer](https://developer.android.google.cn/media/implement/playback-app)
 * - [The Player Interface](https://developer.android.google.cn/media/media3/session/player?hl=en)
 * */
abstract class AbsPlayService : MediaSessionService() {

    protected var exoPlayer: ExoPlayer? = null
    protected var mediaSession: MediaSession? = null

    companion object {
        private const val TAG = "AbsPlayerService"
    }

    override fun onCreate() {
        super.onCreate()
        initPlayer()
        initSession()
    }

    override fun onDestroy() {
        freeMemory()
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    abstract fun playerType(): PlayerType
    abstract fun initPlayer()
    abstract fun initSession()
    abstract fun accessSessionToken(): SessionToken

    open fun freeMemory() {
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null

        mediaSession?.player?.stop()
        mediaSession?.player?.release()
        mediaSession?.release()
        mediaSession = null
    }
}