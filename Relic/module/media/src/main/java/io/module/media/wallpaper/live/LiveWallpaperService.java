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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class LiveWallpaperService extends WallpaperService {

    private static final String TAG = "LiveWallpaperService";
    private static final String KEY_ACTION_CHANGE_LIVE_WALLPAPER = "key_action_change_live_wallpaper";
    private static final int KEY_ACTION_CODE_CHANGE_LIVE_WALLPAPER = 10086;

    private volatile Engine liveWallpaperEngine;

    public static void changeWallpaper(Context context, Uri uri) {
        //
    }

    public static void changeWallpaper(Context context, InputStream inputStream) {
        //
    }

    /* ======================== Engine ======================== */

    public class LiveWallpaperEngine extends WallpaperService.Engine {

        private MediaPlayer mediaPlayer;

        private LiveWallpaperGLSurfaceView glSurfaceView;

        private LiveWallpaperVideoRender videoRender;

        /* ======================== override ======================== */

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            initialization();
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
        }

        /* ======================== Logical ======================== */

        private void initialization() {
            initVideoRender();
            initGLSurfaceView();
        }

        private void initVideoRender() {
            videoRender = new LiveWallpaperVideoRender();
        }

        private void initGLSurfaceView() {
            Context context = getApplicationContext();
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            glSurfaceView = new LiveWallpaperGLSurfaceView(context, surfaceHolder);
            glSurfaceView.setRenderer(videoRender);
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            registerBroadcastReceiver();
        }

        @SuppressLint("UnspecifiedRegisterReceiverFlag")
        private void registerBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter(KEY_ACTION_CHANGE_LIVE_WALLPAPER);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        onReceiveAction(intent);
                    }
                }, intentFilter, Context.RECEIVER_EXPORTED);
            } else {
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        onReceiveAction(intent);
                    }
                }, intentFilter);
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

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //
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