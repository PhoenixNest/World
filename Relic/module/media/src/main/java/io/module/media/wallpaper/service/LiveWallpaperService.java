package io.module.media.wallpaper.service;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {

    private volatile Engine liveWallpaperEngine;

    /**
     * Must be implemented to return a new instance of the wallpaper's engine.
     * Note that multiple instances may be active at the same time, such as
     * when the wallpaper is currently set as the active wallpaper and the user
     * is in the wallpaper picker viewing a preview of it as well.
     */
    @Override
    public Engine onCreateEngine() {
        if (liveWallpaperEngine != null) {
            return liveWallpaperEngine;
        }

        liveWallpaperEngine = new LiveWallpaperEngine();
        return liveWallpaperEngine;
    }

    class LiveWallpaperEngine extends WallpaperService.Engine {

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        /**
         * Convenience for {@link SurfaceHolder.Callback#surfaceChanged
         * SurfaceHolder.Callback.surfaceChanged()}.
         *
         * @param holder
         * @param format
         * @param width
         * @param height
         */
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Canvas canvas = holder.lockCanvas();
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setStrokeWidth(10);
            p.setColor(Color.BLUE);
            canvas.drawLine(0, 0, 100, 100, p);
            holder.unlockCanvasAndPost(canvas);
        }

        /**
         * Convenience for {@link SurfaceHolder.Callback#surfaceDestroyed
         * SurfaceHolder.Callback.surfaceDestroyed()}.
         *
         * @param holder
         */
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        /**
         * Called right before the engine is going away.  After this the
         * surface will be destroyed and this Engine object is no longer
         * valid.
         */
        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }
}