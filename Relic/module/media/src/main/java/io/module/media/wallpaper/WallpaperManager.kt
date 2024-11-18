package io.module.media.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import io.module.media.utils.MediaLogUtil
import io.module.media.wallpaper.live.LiveWallpaperService
import java.io.InputStream

object WallpaperManager {

    private const val TAG = "WallpaperManager"

    /* ======================== Built-in tools function ======================== */

    fun setBitmapWallpaper(
        context: Context,
        bitmap: Bitmap,
        type: WallpaperType = WallpaperType.SYSTEM
    ) {
        WallpaperManager.getInstance(context).setBitmap(
            /* fullImage = */ bitmap,
            /* visibleCropHint = */ null,
            /* allowBackup = */ false,
            /* which = */ type.typeId
        ).also {
            MediaLogUtil.d(TAG, "[Set `Bitmap` wallpaper] Success.")
        }
    }

    fun setWallpaper(
        context: Context,
        resId: Int,
        type: WallpaperType = WallpaperType.SYSTEM
    ) {
        WallpaperManager.getInstance(context).setResource(
            /* resid = */ resId,
            /* which = */ type.typeId
        ).also {
            MediaLogUtil.d(TAG, "[Set `Res` wallpaper] Success.")
        }
    }

    fun setStreamWallpaper(
        context: Context,
        inputStream: InputStream,
        type: WallpaperType = WallpaperType.SYSTEM
    ) {
        WallpaperManager.getInstance(context).setStream(
            /* bitmapData = */ inputStream,
            /* visibleCropHint = */ null,
            /* allowBackup = */ false,
            /* which = */ type.typeId
        ).also {
            MediaLogUtil.d(TAG, "[Set `Stream` wallpaper] Success.")
        }
    }

    fun launchSystemWallpaperPicker(context: Context) {
        val intent = Intent(Intent.ACTION_SET_WALLPAPER)
        context.startActivity(intent)
    }

    fun clearWallpaper(
        context: Context,
        type: WallpaperType
    ) {
        WallpaperManager.getInstance(context).clear(type.typeId)
    }

    /* ======================== Service type function ======================== */

    fun setLiveWallpaper(context: Context, wallpaperUri: Uri) {
        val wallpaperService = LiveWallpaperService()
        val componentName = ComponentName(context, wallpaperService::class.java)
        context.startActivity(
            Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
                putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, componentName)
            }
        )
    }
}