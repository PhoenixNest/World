package io.common.system

import android.content.Context
import java.io.File

object FileUtil {

    fun getLocalCacheDirPath(context: Context): String {
        return context.cacheDir.absolutePath
    }

    fun getLocalPublicDirPath(context: Context): String {
        return context.filesDir.absolutePath
    }

    fun getDownloadFileName(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    fun checkFileExist(fileName: String): Boolean {
        val file = File(fileName)
        return file.exists()
    }

    fun createCacheFile(context: Context, fileName: String): File {
        val localCacheDirPath = getLocalCacheDirPath(context)
        return File(localCacheDirPath, fileName)
    }
}