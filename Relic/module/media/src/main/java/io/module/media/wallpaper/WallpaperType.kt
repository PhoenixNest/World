package io.module.media.wallpaper

import android.app.WallpaperManager

enum class WallpaperType(val typeId: Int) {
    SYSTEM(WallpaperManager.FLAG_SYSTEM),
    LOCK_SCREEN(WallpaperManager.FLAG_LOCK)
}