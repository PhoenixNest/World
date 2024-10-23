package io.module.media.wallpaper.live;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.module.media.utils.MediaLogUtil;

public class LiveWallpaperService extends WallpaperService {

    private static final String TAG = "LiveWallpaperService";

    private static final String KEY_ACTION_CHANGE_LIVE_WALLPAPER = "key_action_change_live_wallpaper";

    private static final int KEY_ACTION_CODE_CHANGE_LIVE_WALLPAPER = 10086;

    private volatile Engine liveWallpaperEngine;

    public static void changeWallpaper(Context context, Uri uri) {
        LiveWallpaperModel model = LiveWallpaperModel.getInstance(context);
        model.setUri(uri);
    }

    public static void changeWallpaper(Context context, InputStream inputStream) {
        //
    }

    /* ======================== Engine ======================== */

    public class LiveWallpaperEngine extends WallpaperService.Engine {

        private static final int EGL_VERSION = 2;

        private LiveWallpaperGLSurfaceView glSurfaceView;

        private LiveWallpaperVideoRender videoRender;

        private BroadcastReceiver liveWallpaperChangeReceiver;

        /* ======================== override ======================== */

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            initialization();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            toggleGLSurfaceViewStatus(visible);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            onDestroyAction();
        }

        /* ======================== Logical ======================== */

        private void initialization() {
            initVideoRender();
            initGLSurfaceView();
        }

        private void initVideoRender() {
            videoRender = new LiveWallpaperVideoRender(getApplicationContext());
        }

        private void initGLSurfaceView() {
            Context context = getApplicationContext();
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            glSurfaceView = new LiveWallpaperGLSurfaceView(context, surfaceHolder);
            glSurfaceView.setEGLContextClientVersion(EGL_VERSION);
            glSurfaceView.setRenderer(videoRender);
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            registerBroadcastReceiver();
        }

        @SuppressLint("UnspecifiedRegisterReceiverFlag")
        private void registerBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter(KEY_ACTION_CHANGE_LIVE_WALLPAPER);
            liveWallpaperChangeReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveAction(intent);
                }
            };

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(liveWallpaperChangeReceiver, intentFilter, Context.RECEIVER_EXPORTED);
            } else {
                registerReceiver(liveWallpaperChangeReceiver, intentFilter);
            }
        }

        private void onReceiveAction(Intent intent) {
            if (intent == null) {
                return;
            }

            int actionCode = intent.getIntExtra(KEY_ACTION_CHANGE_LIVE_WALLPAPER, -1);
            if (actionCode == KEY_ACTION_CODE_CHANGE_LIVE_WALLPAPER) {
                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        //
                    }
                });
            }
        }

        private void onDestroyAction() {
            videoRender.onDestroy();
            glSurfaceView.onDestroy();
            unregisterReceiver(liveWallpaperChangeReceiver);
        }

        private void toggleGLSurfaceViewStatus(boolean isVisible) {
            if (isVisible) {
                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            } else {
                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }

    @Override
    public Engine onCreateEngine() {
        if (liveWallpaperEngine != null) {
            return liveWallpaperEngine;
        }

        liveWallpaperEngine = new LiveWallpaperEngine();
        return liveWallpaperEngine;
    }

    /* ======================== Video render ======================== */

    private static class LiveWallpaperVideoRender implements GLSurfaceView.Renderer {

        private static final String TAG = "LiveWallpaperVideoRender";

        private final WeakReference<Context> contextWeakReference;

        private MediaPlayer mediaPlayer;

        private int videoWidth;

        private int videoHeight;

        public LiveWallpaperVideoRender(Context context) {
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            MediaLogUtil.d(TAG, "onSurfaceCreated");
            Context context = contextWeakReference.get();
            LiveWallpaperModel model = LiveWallpaperModel.getInstance(context);
            mediaPlayer = MediaPlayer.create(context, model.getUri());
            playNewVideo();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //
        }

        public void playVideo() {
            if (mediaPlayer == null) {
                return;
            }

            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

        public void pauseVideo() {
            if (mediaPlayer == null) {
                return;
            }

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }

        public void onDestroy() {
            if (mediaPlayer == null) {
                return;
            }

            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        private void playNewVideo() {
            if (mediaPlayer == null) {
                return;
            }

            try {
                Context context = contextWeakReference.get();
                LiveWallpaperModel model = LiveWallpaperModel.getInstance(context);
                mediaPlayer.reset();
                mediaPlayer.setDataSource(context, model.getUri());
                mediaPlayer.setVolume(0, 0);
                mediaPlayer.setOnPreparedListener(player -> {
                    player.setLooping(true);
                    // player.setScreenOnWhilePlaying(true);
                    player.start();
                });
                mediaPlayer.setOnVideoSizeChangedListener((player, width, height) -> {
                    videoWidth = width;
                    videoHeight = height;
                });
                mediaPlayer.setOnErrorListener((player, what, extra) -> {
                    String errorMessage = String.format("[Play New Video] Error, Type: $1%s, ExtraCode: $2%s)", what, extra);
                    player.pause();
                    MediaLogUtil.e(TAG, errorMessage, null);
                    return false;
                });
                mediaPlayer.prepareAsync();
            } catch (Exception exception) {
                MediaLogUtil.e(TAG, String.format(exception.getMessage()), exception);
            }
        }
    }

    /* ======================== GLSurfaceView ======================== */

    private static class LiveWallpaperGLSurfaceView extends GLSurfaceView {

        private SurfaceHolder surfaceHolder;

        public LiveWallpaperGLSurfaceView(Context context) {
            super(context);
        }

        public LiveWallpaperGLSurfaceView(Context context, SurfaceHolder surfaceHolder) {
            super(context);
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public SurfaceHolder getHolder() {
            return surfaceHolder;
        }

        public void onDestroy() {
            onDetachedFromWindow();
        }
    }
}