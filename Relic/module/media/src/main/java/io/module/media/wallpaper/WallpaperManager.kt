package io.module.media.wallpaper

import android.Manifest
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.ContextCompat
import io.module.media.utils.MediaLogUtil
import io.module.media.wallpaper.service.LiveWallpaperService
import io.module.media.wallpaper.service.StaticWallpaperService
import java.io.InputStream

object WallpaperManager {

    private const val TAG = "WallpaperManager"

    fun checkPermission(
        context: Context,
        permissionString: String = Manifest.permission.SET_WALLPAPER
    ): Boolean {
        return ContextCompat.checkSelfPermission(
            /* context = */ context,
            /* permission = */ permissionString
        ) == PackageManager.PERMISSION_GRANTED
    }

    /* ======================== Built-in tools function ======================== */

    fun setBitmapWallpaper(
        context: Context,
        bitmap: Bitmap
    ) {
        if (checkPermission(context)) {
            WallpaperManager.getInstance(context).setBitmap(
                /* fullImage = */ bitmap,
                /* visibleCropHint = */ null,
                /* allowBackup = */ true
            )
        } else {
            MediaLogUtil.w(TAG, "[Set wallpaper] Failed, please make sure you have SET_WALLPAPER permission already.")
        }
    }

    fun setWallpaper(
        context: Context,
        resId: Int
    ) {
        if (checkPermission(context)) {
            WallpaperManager.getInstance(context).setResource(resId)
        } else {
            MediaLogUtil.w(TAG, "[Set wallpaper] Failed, please make sure you have SET_WALLPAPER permission already.")
        }
    }

    fun setStreamWallpaper(context: Context, inputStream: InputStream) {
        if (checkPermission(context)) {
            WallpaperManager.getInstance(context).setStream(inputStream)
        } else {
            MediaLogUtil.w(TAG, "[Set wallpaper] Failed, please make sure you have SET_WALLPAPER permission already.")
        }
    }

    fun launchSystemWallpaperPicker(context: Context) {
        val intent = Intent(Intent.ACTION_SET_WALLPAPER)
        context.startActivity(intent)
    }

    fun clearWallpaper(context: Context) {
        if (checkPermission(context)) {
            WallpaperManager.getInstance(context).clear()
        } else {
            MediaLogUtil.w(TAG, "[Clear wallpaper] Failed, please make sure you have SET_WALLPAPER permission already.")
        }
    }

    /* ======================== Service type function ======================== */

    fun setStaticWallpaper(context: Context, wallpaperUri: Uri) {
        val wallpaperService = StaticWallpaperService()
        val componentName = ComponentName(context, wallpaperService::class.java)
        context.startActivity(
            Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            ).apply {
                putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, componentName)
            }
        )
    }

    fun setLiveWallpaper(context: Context, wallpaperUri: Uri) {
        val wallpaperService = LiveWallpaperService()
        val componentName = ComponentName(context, wallpaperService::class.java)
        context.startActivity(
            Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            ).apply {
                putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, componentName)
            }
        )
    }
}